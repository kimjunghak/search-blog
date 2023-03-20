package com.serach.blog.api.result.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.serach.blog.api.result.naver.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Document {

    private String title;

    private String contents;

    private String url;

    private String blogname;

    private String thumbnail;

    private Date datetime;

    public static Document toDocument(Item item) {
        Document document = new Document();
        document.setTitle(item.getTitle());
        document.setUrl(item.getLink());
        document.setBlogname(item.getBloggername());
        document.setContents(item.getDescription());
        LocalDateTime localDateTime = LocalDateTime.from(item.getPostdate().atStartOfDay());
        document.setDatetime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        return document;
    }
}
