package com.serach.blog.service.search;

import com.serach.blog.api.result.ApiResult;
import com.serach.blog.model.params.RequestParams;

public interface BlogSearchService {

    ApiResult search(RequestParams params);
}
