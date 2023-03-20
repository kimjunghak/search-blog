package com.serach.blog.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api.kakao")
public class KakaoProperties {

    private String blogUrl;

    private String token;
}

