package com.serach.blog.api.result;

import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.api.result.kakao.Meta;

import java.util.List;

public interface ApiResult {

    Meta getMeta();

    List<Document> getDocuments();
}
