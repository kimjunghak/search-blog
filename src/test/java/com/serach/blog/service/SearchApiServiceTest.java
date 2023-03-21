package com.serach.blog.service;

import com.serach.blog.api.result.kakao.Document;
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
import org.springframework.data.domain.PageImpl;

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
    private KakaoSearchService kakaoSearchService;

    @Mock
    private NaverSearchService naverSearchService;

    @BeforeEach
    void setup() {
        searchApiService = new SearchApiService(popularKeywordService, mqService, kakaoSearchService, naverSearchService);
    }

    @Test
    @DisplayName("API 호출 성공")
    void blogSearch() {
        String url = "https://yangbongsoo.tistory.com";

        RequestParams requestParams = new RequestParams();
        requestParams.setUrl(url);

        PageImpl<Document> documents = new PageImpl<>(Collections.singletonList(new Document()));
        given(kakaoSearchService.search(any())).willReturn(documents);

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