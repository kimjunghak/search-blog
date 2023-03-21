package com.serach.blog.api.result.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.serach.blog.api.result.ApiResult;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoApiResult implements ApiResult {

    private Meta meta;

    private List<Document> documents;
}
