package com.serach.blog.service;

import com.serach.blog.api.properties.ApiProperties;
import com.serach.blog.api.result.Document;
import com.serach.blog.api.result.KakaoApiResult;
import com.serach.blog.api.result.Meta;
import com.serach.blog.api.service.ApiService;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.RestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SearchApiServiceTest {

    private SearchApiService searchApiService;

    @Mock
    PopularKeywordService popularKeywordService;

    @Mock
    ApiService apiService;

    @Mock
    MqService mqService;

    @BeforeEach
    void setup() {
        ApiProperties apiProperties = new ApiProperties();
        searchApiService = new SearchApiService(popularKeywordService, apiService, mqService, apiProperties);
    }

    @Test
    void blogSearch() {
        String url = "https://yangbongsoo.tistory.com";

        RequestParams requestParams = new RequestParams();
        requestParams.setUrl(url);

        KakaoApiResult kakaoApiResult = new KakaoApiResult();
        Meta meta = new Meta();
        ArrayList<Document> documents = new ArrayList<>();
        kakaoApiResult.setMeta(meta);
        kakaoApiResult.setDocuments(documents);

        given(apiService.get(any(), any(), any(), any())).willReturn(kakaoApiResult);

        RestResult restResult = searchApiService.blogSearch(requestParams);

        assertTrue(restResult.isSuccess());
    }

    @Test
    void searchPopularKeyword() {
        List<PopularKeyword> popularKeywords = List.of(
                PopularKeyword.builder()
                        .keyword("테스트")
                        .count(1)
                        .build(),
                PopularKeyword.builder()
                        .keyword("테스트2")
                        .count(2)
                        .build(),
                PopularKeyword.builder()
                        .keyword("테스트3")
                        .count(3)
                        .build()
        );
        given(popularKeywordService.top10Keyword()).willReturn(popularKeywords);

        RestResult restResult = searchApiService.searchPopularKeyword();
        assertTrue(restResult.isSuccess());
    }
}