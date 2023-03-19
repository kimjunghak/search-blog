package com.serach.blog.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mq")
public class MqProperties {

    private String host;

    private Integer port;

    private String virtualHost;

    private String username;

    private String password;

    private String exchange;

    private String queueKeyword;
}
