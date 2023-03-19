package com.serach.blog.service;

import com.serach.blog.api.params.KakaoParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.ApiProperties;
import com.serach.blog.api.result.Document;
import com.serach.blog.api.result.KakaoApiResult;
import com.serach.blog.api.service.ApiService;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.PopularKeywordResult;
import com.serach.blog.model.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchApiService {

    private final PopularKeywordService popularKeywordService;
    private final ApiService apiService;
    private final MqService mqService;

    private final ApiProperties apiProperties;

    public RestResult blogSearch(RequestParams params) {
        //키워드 저장
        String keyword = params.getKeyword();
        if(keyword != null) {
            mqService.sendMq(keyword);
        }

        //파라미터 세팅
        KakaoParams kakaoParams = KakaoParams.defaultKakaoParams(params);
        MultiValueMap<String, String> kakaoParameters = Parameters.extract(RegistrationIds.kakao.name(), kakaoParams);

        KakaoApiResult kakaoApiResult = apiService.get(apiProperties.getKakaoToken(), apiProperties.getKakaoUrl(), kakaoParameters, KakaoApiResult.class);
        //todo 에러발생 시 네이버

        PageImpl<Document> result = new PageImpl<>(kakaoApiResult.getDocuments());
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
