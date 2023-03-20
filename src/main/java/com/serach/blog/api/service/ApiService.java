package com.serach.blog.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final WebClient webClient;

    public <T> T get(String url, MultiValueMap<String, String> headers, MultiValueMap<String, String> parameters, Class<T> clazz) {
        return webClient.get()
                .uri(url, uriBuilder -> uriBuilder
                        .queryParams(parameters)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is5xxServerError() || httpStatus.is4xxClientError(), ClientResponse::createException)
                .bodyToMono(clazz)
                .block();
    }
}
