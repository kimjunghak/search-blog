package com.serach.blog.listener;

import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.service.PopularKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final PopularKeywordService popularKeywordService;

    @RabbitListener(queues = "keyword")
    public void receiveMessage(Message message) {
        String keyword = new String(message.getBody());
        log.info("<---- Message Listener [SUCCESS] {}", keyword);
        saveKeywordData(keyword);
    }

    private void saveKeywordData(String keyword) {
        Optional<PopularKeyword> byKeyword = popularKeywordService.findByKeyword(keyword);
        PopularKeyword popularKeyword;
        if (byKeyword.isEmpty()) {
            //없을 경우 추가
            popularKeyword = PopularKeyword.builder()
                    .keyword(keyword)
                    .count(1)
                    .build();
        } else {
            //이미 존재할 시 카운트 증가
            popularKeyword = byKeyword.get();
            popularKeyword.setCount(popularKeyword.getCount() + 1);
        }
        popularKeywordService.saveKeyword(popularKeyword);
    }
}
