package com.serach.blog.controller;

import com.serach.blog.model.result.PopularKeywordResult;
import com.serach.blog.model.result.RestResult;
import com.serach.blog.service.SearchApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private SearchApiService searchApiService;

    @Test
    @DisplayName("url 없음")
    void url_is_null() throws Exception {
        String contentAsString = mock.perform(MockMvcRequestBuilders.get("/search/blog"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(contentAsString).contains("url must not null");
    }

    @Test
    @DisplayName("page 범위 초과")
    void page_out_of_range() throws Exception {
        String contentAsString = mock.perform(MockMvcRequestBuilders.get("/search/blog")
                .param("url", "https://yangbongsoo.tistory.com")
                .param("page", "100"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(contentAsString).contains("1 <= page <= 50");
    }

    @Test
    @DisplayName("size 범위 초과")
    void size_out_of_range() throws Exception {
        String contentAsString = mock.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("url", "https://yangbongsoo.tistory.com")
                        .param("size", "100"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(contentAsString).contains("1 <= size <= 50");
    }

    @Test
    @DisplayName("지원하지 않는 정렬")
    void not_support_sort() throws Exception {
        String contentAsString = mock.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("url", "https://yangbongsoo.tistory.com")
                        .param("sort", "test"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(contentAsString).contains("not support sort");
    }

    @Test
    @DisplayName("정상 호출")
    void searchBlog() throws Exception {

        given(searchApiService.blogSearch(any())).willReturn(RestResult.success(""));

        mock.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("url", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("인기 검색어 Top10")
    void testSearchPopularKeyword() throws Exception {

        List<PopularKeywordResult> popularKeywordResults = List.of(new PopularKeywordResult("테스트", 1), new PopularKeywordResult("테스트2", 2), new PopularKeywordResult("테스트3", 3));

        given(searchApiService.searchPopularKeyword()).willReturn(RestResult.success(popularKeywordResults));

        mock.perform(MockMvcRequestBuilders.get("/search/popular-keyword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}