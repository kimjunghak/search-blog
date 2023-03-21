package com.serach.blog.service;

import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.PopularKeywordResult;
import com.serach.blog.model.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchApiService {

    private final PopularKeywordService popularKeywordService;
    private final MqService mqService;

    private final KakaoSearchService kakaoSearchService;
    private final NaverSearchService naverSearchService;

    public RestResult blogSearch(RequestParams params) {
        //키워드 저장
        String keyword = params.getKeyword();
        if(keyword != null) {
            mqService.sendMq(keyword);
        }

        Page<Document> result;
        try {
            result = kakaoSearchService.search(params);
        } catch (WebClientResponseException e) {
            result = naverSearchService.search(params);
        }
        return RestResult.success(result);
    }

    public RestResult searchPopularKeyword() {
        List<PopularKeyword> keywords = popularKeywordService.top10Keyword();

        ArrayList<PopularKeywordResult> top10 = new ArrayList<>();
        for (PopularKeyword popularKeyword : keywords) {
            top10.add(PopularKeywordResult.toResult(popularKeyword));
        }
        return RestResult.success(top10);
    }
}
