package com.serach.blog.service;

import com.serach.blog.api.params.NaverParams;
import com.serach.blog.api.params.Parameters;
import com.serach.blog.api.params.RegistrationIds;
import com.serach.blog.api.properties.NaverProperties;
import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.naver.Item;
import com.serach.blog.api.result.naver.NaverApiResult;
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

import java.util.ArrayList;
import java.util.List;

import static com.serach.blog.api.result.kakao.Document.toDocument;

@Service
@RequiredArgsConstructor
public class NaverSearchService implements BlogSearch{

    private final ApiService apiService;

    private final NaverProperties naverProperties;

    @Override
    public Page<Document> search(RequestParams params) {
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
        //Spring 의 Page는 0부터 시작하기 떄문에 수정
        PageRequest pageRequest = PageRequest.of(naverApiResult.getStart() - 1, naverApiResult.getDisplay(), Sort.by(params.getSort()));
        return new PageImpl<>(documents, pageRequest, naverApiResult.getTotal());
    }
}
