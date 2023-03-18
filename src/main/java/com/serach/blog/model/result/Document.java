package com.serach.blog.model.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    private String title;

    private String contents;

    private String url;

    private String blogname;

    private String thumbnail;

    private LocalDateTime dateTime;
}
