package com.serach.blog.api.result.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.serach.blog.api.result.ApiResult;
import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.kakao.Meta;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.serach.blog.api.result.kakao.Document.toDocument;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverApiResult implements ApiResult {

    private Date lastBuildDate;

    private Integer total;

    private Integer start;

    private Integer display;

    private List<Item> items;

    @Override
    public Meta getMeta() {
        Meta meta = new Meta();
        meta.setPageableCount(display);
        meta.setTotalCount(total);
        return meta;
    }

    @Override
    public List<Document> getDocuments() {
        ArrayList<Document> documents = new ArrayList<>();
        for (Item item : items) {
            documents.add(toDocument(item));
        }
        return documents;
    }
}