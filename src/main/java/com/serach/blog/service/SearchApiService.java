package com.serach.blog.service;

import com.serach.blog.api.result.ApiResult;
import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.kakao.Meta;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.PopularKeywordResult;
import com.serach.blog.model.result.RestResult;
import com.serach.blog.service.search.BlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchApiService {

    private final PopularKeywordService popularKeywordService;
    private final MqService mqService;

    private final List<BlogSearchService> blogSearchServices;

    public RestResult blogSearch(RequestParams params) {
        //키워드 저장
        String keyword = params.getKeyword();
        if(keyword != null) {
            mqService.sendMq(keyword);
        }
        return RestResult.success(getResult(params));
    }

    public RestResult searchPopularKeyword() {
        List<PopularKeyword> keywords = popularKeywordService.top10Keyword();

        ArrayList<PopularKeywordResult> top10 = new ArrayList<>();
        for (PopularKeyword popularKeyword : keywords) {
            top10.add(PopularKeywordResult.toResult(popularKeyword));
        }
        return RestResult.success(top10);
    }

    private Page<Document> getResult(RequestParams params) {
        // API 호출 시도
        for (BlogSearchService blogSearchService : blogSearchServices) {
            try {
                ApiResult apiResult = blogSearchService.search(params);
                Meta meta = apiResult.getMeta();
                List<Document> documents = apiResult.getDocuments();
                PageRequest pageRequest = PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by(params.getSort()));
                return new PageImpl<>(documents, pageRequest, meta.getTotalCount());
            } catch (WebClientResponseException e) {
                log.warn("Api 호출 실패 cause: {}", e.getMessage());
            }
        }

        throw new RuntimeException("Api 호출 실패");
    }
}
