package com.serach.blog.service;

import com.serach.blog.api.params.KakaoParams;
import com.serach.blog.api.params.NaverParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.KakaoProperties;
import com.serach.blog.api.properties.NaverProperties;
import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.kakao.KakaoApiResult;
import com.serach.blog.api.result.naver.Item;
import com.serach.blog.api.result.naver.NaverApiResult;
import com.serach.blog.api.service.ApiService;
import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.result.PopularKeywordResult;
import com.serach.blog.model.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

import static com.serach.blog.api.result.kakao.Document.toDocument;

@Service
@RequiredArgsConstructor
public class SearchApiService {

    private final PopularKeywordService popularKeywordService;
    private final ApiService apiService;
    private final MqService mqService;

    private final KakaoProperties kakaoProperties;
    private final NaverProperties naverProperties;

    public RestResult blogSearch(RequestParams params) {
        //키워드 저장
        String keyword = params.getKeyword();
        if(keyword != null) {
            mqService.sendMq(keyword);
        }

        Page<Document> result;
        try {
            result = kakaoSearch(params);
        } catch (WebClientResponseException e) {
            result = naverSearch(params);
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

    private Page<Document> kakaoSearch(RequestParams params) {
        //헤더 세팅
        MultiValueMap<String, String> kakaoHeaders = new LinkedMultiValueMap<>();
        kakaoHeaders.add("Authorization", "KakaoAK " + kakaoProperties.getToken());
        //파라미터 세팅
        MultiValueMap<String, String> kakaoParameters = Parameters.extract(RegistrationIds.kakao, KakaoParams.toKakaoParams(params));
        KakaoApiResult kakaoApiResult = apiService.get(kakaoProperties.getBlogUrl(), kakaoHeaders, kakaoParameters, KakaoApiResult.class);

        return new PageImpl<>(kakaoApiResult.getDocuments());
    }

    private Page<Document> naverSearch(RequestParams params) {
        //헤더 세팅
        MultiValueMap<String, String> naverHeaders = new LinkedMultiValueMap<>();
        naverHeaders.add("X-Naver-Client-Id", naverProperties.getClientId());
        naverHeaders.add("X-Naver-Client-Secret", naverProperties.getClientSecret());
        //파라미터 세팅
        MultiValueMap<String, String> naverParameters = Parameters.extract(RegistrationIds.naver, NaverParams.toNaverParams(params));
        NaverApiResult naverApiResult = apiService.get(naverProperties.getBlogUrl(), naverHeaders, naverParameters, NaverApiResult.class);

        ArrayList<Document> documents = new ArrayList<>();
        List<Item> items = naverApiResult.getItems();
        for (Item item : items) {
            documents.add(toDocument(item));
        }
        return new PageImpl<>(documents);
    }
}
