package com.serach.blog.service;

import com.serach.blog.api.params.KakaoParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.KakaoProperties;
import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.kakao.KakaoApiResult;
import com.serach.blog.api.result.kakao.Meta;
import com.serach.blog.api.service.ApiService;
import com.serach.blog.model.params.RequestParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class KakaoSearchService implements BlogSearch{

    private final ApiService apiService;

    private final KakaoProperties kakaoProperties;

    @Override
    public Page<Document> search(RequestParams params) {
        //헤더 세팅
        MultiValueMap<String, String> kakaoHeaders = new LinkedMultiValueMap<>();
        kakaoHeaders.add("Authorization", "KakaoAK " + kakaoProperties.getToken());
        //파라미터 세팅
        MultiValueMap<String, String> kakaoParameters = Parameters.extract(RegistrationIds.kakao, KakaoParams.toKakaoParams(params));
        KakaoApiResult kakaoApiResult = apiService.get(kakaoProperties.getBlogUrl(), kakaoHeaders, kakaoParameters, KakaoApiResult.class);

        Meta meta = kakaoApiResult.getMeta();
        //Spring 의 Page는 0부터 시작하기 떄문에 수정
        PageRequest pageRequest = PageRequest.of(params.getPage() - 1, meta.getPageableCount(), Sort.by(params.getSort()));
        return new PageImpl<>(kakaoApiResult.getDocuments(), pageRequest, meta.getTotalCount());
    }
}
