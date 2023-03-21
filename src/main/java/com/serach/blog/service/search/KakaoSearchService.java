package com.serach.blog.service.search;

import com.serach.blog.api.params.KakaoParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.KakaoProperties;
import com.serach.blog.api.result.kakao.KakaoApiResult;
import com.serach.blog.model.params.RequestParams;
import com.serach.blog.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Order(1)
public class KakaoSearchService implements BlogSearchService{

    private final ApiService apiService;

    private final KakaoProperties kakaoProperties;

    @Override
    public KakaoApiResult search(RequestParams params) {
        //헤더 세팅
        MultiValueMap<String, String> kakaoHeaders = new LinkedMultiValueMap<>();
        kakaoHeaders.add("Authorization", "KakaoAK " + kakaoProperties.getToken());
        //파라미터 세팅
        MultiValueMap<String, String> kakaoParameters = Parameters.extract(RegistrationIds.kakao, KakaoParams.toKakaoParams(params));
        return apiService.get(kakaoProperties.getBlogUrl(), kakaoHeaders, kakaoParameters, KakaoApiResult.class);
    }
}
