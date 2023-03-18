package com.serach.blog.service;

import com.serach.blog.api.params.KakaoParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.ApiProperties;
import com.serach.blog.api.service.ApiService;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.Document;
import com.serach.blog.model.result.KakaoApiResult;
import com.serach.blog.model.result.PopularKeywordResult;
import com.serach.blog.model.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchApiService {

    private final PopularKeywordService popularKeywordService;
    private final ApiService apiService;

    private final ApiProperties apiProperties;

    public RestResult blogSearch(RequestParams params) {
        //키워드 저장
        String keyword = params.getKeyword();
        if(keyword != null) {
            saveKeywordData(keyword);
        }

        //파라미터 세팅
        KakaoParams kakaoParams = KakaoParams.defaultKakaoParams(params);
        MultiValueMap<String, String> kakaoParameters = Parameters.extract(RegistrationIds.kakao.name(), kakaoParams);

        KakaoApiResult kakaoApiResult = apiService.get(apiProperties.getKakaoToken(), apiProperties.getKakaoUrl(), kakaoParameters, KakaoApiResult.class);
        //todo 에러발생 시 네이버

        PageRequest pageRequest = PageRequest.of(params.getPage(), params.getSize(), Sort.by(params.getSort()));
        PageImpl<Document> result = new PageImpl<>(kakaoApiResult.getDocuments(), pageRequest, kakaoApiResult.getMeta().getTotalCount());
        return RestResult.success(result);
    }

    private void saveKeywordData(String keyword) {
        Optional<PopularKeyword> byKeyword = popularKeywordService.findByKeyword(keyword);
        if (byKeyword.isEmpty()) {
            //없을 경우 추가
            PopularKeyword newKeyword = PopularKeyword.builder()
                    .keyword(keyword)
                    .count(1)
                    .build();

            //todo mq로 수정, 동시성 이슈
            popularKeywordService.saveKeyword(newKeyword);
        } else {
            //이미 존재할 시 카운트 증가
            PopularKeyword popularKeyword = byKeyword.get();
            popularKeyword.setCount(popularKeyword.getCount() + 1);
            popularKeywordService.saveKeyword(popularKeyword);
        }
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
