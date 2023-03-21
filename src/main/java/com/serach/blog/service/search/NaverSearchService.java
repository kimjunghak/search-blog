package com.serach.blog.service.search;

import com.serach.blog.api.params.NaverParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.NaverProperties;
import com.serach.blog.api.result.naver.NaverApiResult;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Order(2)
public class NaverSearchService implements BlogSearchService{

    private final ApiService apiService;

    private final NaverProperties naverProperties;

    @Override
    public NaverApiResult search(RequestParams params) {
        //헤더 세팅
        MultiValueMap<String, String> naverHeaders = new LinkedMultiValueMap<>();
        naverHeaders.add("X-Naver-Client-Id", naverProperties.getClientId());
        naverHeaders.add("X-Naver-Client-Secret", naverProperties.getClientSecret());
        //파라미터 세팅
        MultiValueMap<String, String> naverParameters = Parameters.extract(RegistrationIds.naver, NaverParams.toNaverParams(params));
        return apiService.get(naverProperties.getBlogUrl(), naverHeaders, naverParameters, NaverApiResult.class);
    }
}
