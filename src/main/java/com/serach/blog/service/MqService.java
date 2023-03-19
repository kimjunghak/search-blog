package com.serach.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serach.blog.model.properties.MqProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqService {

    private final RabbitTemplate rabbitTemplate;

    private final MqProperties mqProperties;
    private final ObjectMapper objectMapper;

    public void sendMq(String keyword) {
        rabbitTemplate.convertAndSend(mqProperties.getExchange(), mqProperties.getQueueKeyword(), buildMessage(keyword));
    }

    private Message buildMessage(Object object) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentEncoding("utf-8");
        return new Message(toJson(object).getBytes(), messageProperties);
    }

    private String toJson(Object object) {
        // String 경우 Double Quotes 이슈
        if (object instanceof String s) {
            return s;
        }
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJson Error");
            e.printStackTrace();
        }

        return json;
    }
}
