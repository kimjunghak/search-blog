package com.serach.blog.api.result.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverApiResult {

    private Date lastBuildDate;

    private Integer total;

    private Integer start;

    private Integer display;

    private List<Item> items;
}