package com.serach.blog.controller;

import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.params.SortEnum;
import com.serach.blog.model.result.RestResult;
import com.serach.blog.service.SearchApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchApiService searchApiService;

    @GetMapping("/search/blog")
    public RestResult searchBlog(@ModelAttribute @Valid RequestParams params, Errors errors) {
        if (errors.hasErrors()) {
            StringJoiner joiner = new StringJoiner(",");
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                String objectName = error.getDefaultMessage();
                joiner.add(objectName);
            }
            throw new IllegalArgumentException(joiner.toString());
        }
        SortEnum.valid(params.getSort());

        return searchApiService.blogSearch(params);
    }

    @GetMapping("/search/popular-keyword")
    public RestResult searchPopularKeyword() {
        return searchApiService.searchPopularKeyword();
    }
}
