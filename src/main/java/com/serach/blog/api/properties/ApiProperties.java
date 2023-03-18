package com.serach.blog.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private String kakaoUrl;

    private String kakaoToken;

    private String naverUrl;
}
