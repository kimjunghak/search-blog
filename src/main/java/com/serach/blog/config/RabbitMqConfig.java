package com.serach.blog.config;


import com.serach.blog.model.properties.MqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final MqProperties mqProperties;

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(mqProperties.getHost(), mqProperties.getPort());
        cachingConnectionFactory.setUsername(mqProperties.getUsername());
        cachingConnectionFactory.setPassword(mqProperties.getPassword());
        cachingConnectionFactory.setVirtualHost(mqProperties.getVirtualHost());

        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    // Queue 선언
    @Bean
    DirectExchange blogExchange() {
        return new DirectExchange(mqProperties.getExchange());
    }

    @Bean
    Queue keywordQueue() {
        return new Queue(mqProperties.getQueueKeyword());
    }

    // Queue 바인딩
    @Bean
    Binding keywordBinding() {
        return BindingBuilder.bind(keywordQueue()).to(blogExchange()).with(keywordQueue().getName());
    }
}
