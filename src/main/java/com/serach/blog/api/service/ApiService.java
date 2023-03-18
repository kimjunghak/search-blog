package com.serach.blog.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final WebClient webClient;

    public <T> T get(String token, String url, MultiValueMap<String, String> parameters, Class<T> clazz) {
        WebClient.RequestHeadersSpec<?> webclient = webClient.get()
                .uri(url, uriBuilder -> uriBuilder
                        .queryParams(parameters)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8);

        if (token != null && url.contains("kakao")) {
            webclient
                    .header("Authorization", "KakaoAK " + token);
        }

        T responseBody = webclient
                .retrieve()
                .bodyToMono(clazz)
                .block();

        log.info("responseBody: {}", responseBody);

        return responseBody;
    }
}
