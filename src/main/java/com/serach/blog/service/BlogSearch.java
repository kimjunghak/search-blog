package com.serach.blog.service;

import com.serach.blog.api.result.kakao.Document;
import com.serach.blog.model.params.RequestParams;
import org.springframework.data.domain.Page;

public interface BlogSearch {

    Page<Document> search(RequestParams params);
}
