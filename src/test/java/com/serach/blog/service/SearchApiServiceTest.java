package com.serach.blog.service;

import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.kakao.KakaoApiResult;
import com.serach.blog.api.result.kakao.Meta;
import com.serach.blog.api.result.naver.Item;
import com.serach.blog.api.result.naver.NaverApiResult;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.RestResult;
import com.serach.blog.service.search.BlogSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SearchApiServiceTest {

    @InjectMocks
    private SearchApiService searchApiService;

    @Mock
    private PopularKeywordService popularKeywordService;

    @Mock
    private MqService mqService;

    @Mock
    private BlogSearchService kakaoSearchService;

    @Mock
    private BlogSearchService naverSearchService;

    @Mock
    private List<BlogSearchService> blogSearchServices;

    @BeforeEach
    void setup() {
        blogSearchServices = List.of(kakaoSearchService, naverSearchService);
        searchApiService = new SearchApiService(popularKeywordService, mqService, blogSearchServices);
    }

    @Test
    @DisplayName("KAKAO API 호출 성공")
    void blogSearch_kakao() {
        String url = "https://yangbongsoo.tistory.com";

        RequestParams requestParams = new RequestParams();
        requestParams.setUrl(url);

        KakaoApiResult kakaoApiResult = new KakaoApiResult();
        Meta meta = new Meta();
        kakaoApiResult.setMeta(meta);
        kakaoApiResult.setDocuments(Collections.singletonList(new Document()));
        given(kakaoSearchService.search(any())).willReturn(kakaoApiResult);

        RestResult restResult = searchApiService.blogSearch(requestParams);

        assertTrue(restResult.isSuccess());
    }

    @Test
    @DisplayName("NAVER API 호출 성공")
    void blogSearch_naver() {
        String url = "https://yangbongsoo.tistory.com";

        RequestParams requestParams = new RequestParams();
        requestParams.setUrl(url);

        given(kakaoSearchService.search(any())).willThrow(WebClientResponseException.class);

        NaverApiResult naverApiResult = new NaverApiResult();
        naverApiResult.setStart(1);
        naverApiResult.setTotal(0);
        naverApiResult.setDisplay(10);
        naverApiResult.setItems(Collections.singletonList(new Item()));

        given(naverSearchService.search(any())).willReturn(naverApiResult);

        RestResult restResult = searchApiService.blogSearch(requestParams);

        assertTrue(restResult.isSuccess());
    }

    @Test
    @DisplayName("인기 키워드 호출")
    void searchPopularKeyword() {
        List<PopularKeyword> popularKeywords = List.of(
                PopularKeyword.builder()
                        .keyword("테스트")
                        .count(3)
                        .build(),
                PopularKeyword.builder()
                        .keyword("테스트2")
                        .count(2)
                        .build(),
                PopularKeyword.builder()
                        .keyword("테스트3")
                        .count(1)
                        .build()
        );
        given(popularKeywordService.top10Keyword()).willReturn(popularKeywords);

        RestResult restResult = searchApiService.searchPopularKeyword();
        assertTrue(restResult.isSuccess());
        assertEquals("테스트", popularKeywords.get(0).getKeyword());
        assertEquals(3, popularKeywords.size());
    }
}