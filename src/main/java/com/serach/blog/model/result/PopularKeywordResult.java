package com.serach.blog.model.result;

import com.serach.blog.model.entity.PopularKeyword;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PopularKeywordResult {

    private String keyword;

    private Integer count;

    public static PopularKeywordResult toResult(PopularKeyword popularKeyword) {
        return new PopularKeywordResult(popularKeyword.getKeyword(), popularKeyword.getCount());
    }
}
