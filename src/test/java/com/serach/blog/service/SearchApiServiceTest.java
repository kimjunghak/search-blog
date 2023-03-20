package com.serach.blog.service;

import com.serach.blog.api.properties.KakaoProperties;
import com.serach.blog.api.properties.NaverProperties;
import com.serach.blog.api.result.kakao.KakaoApiResult;
import com.serach.blog.api.service.ApiService;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.RestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    private ApiService apiService;

    @Mock
    private MqService mqService;

    @Mock
    private KakaoProperties kakaoProperties;

    @Mock
    private NaverProperties naverProperties;

    @BeforeEach
    void setup() {
        searchApiService = new SearchApiService(popularKeywordService, apiService, mqService, kakaoProperties, naverProperties);
    }

    @Test
    @DisplayName("API 호출 성공")
    void blogSearch() {
        String url = "https://yangbongsoo.tistory.com";

        RequestParams requestParams = new RequestParams();
        requestParams.setUrl(url);

        KakaoApiResult kakaoApiResult = new KakaoApiResult();
        kakaoApiResult.setDocuments(new ArrayList<>());

        given(apiService.get(any(), any(), any(), any())).willReturn(kakaoApiResult);

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