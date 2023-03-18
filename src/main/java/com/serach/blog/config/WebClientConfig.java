package com.serach.blog.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.SECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.SECONDS));
                })
                .responseTimeout(Duration.ofSeconds(5));

        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.withDefaults())
                .codecs(configure -> configure.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) //256KB 제한
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
