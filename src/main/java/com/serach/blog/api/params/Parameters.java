package com.serach.blog.api.params;

import lombok.AllArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.function.Function;

@AllArgsConstructor
public enum Parameters {

    KAKAO("KAKAO", (params) -> {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameters.add("query", params.getQuery());
        parameters.add("sort", params.getSort());
        parameters.add("page", params.getPage().toString());
        parameters.add("size", params.getSize().toString());
        return parameters;
    });

    private final String registrationId;
    private final Function<ParamsInterface, MultiValueMap<String, String>> parameters;

    public static MultiValueMap<String, String> extract(String registrationId, ParamsInterface params) {
        return Arrays.stream(Parameters.values())
                .filter(p -> p.registrationId.equalsIgnoreCase(registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .parameters.apply(params);
    }
}
