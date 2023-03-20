package com.serach.blog.service;

import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.repo.PopularKeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PopularKeywordServiceTest {

    @InjectMocks
    private PopularKeywordService popularKeywordService;

    @Mock
    private PopularKeywordRepository popularKeywordRepository;

    private PopularKeyword keyword1;
    private PopularKeyword keyword2;

    @BeforeEach
    void setUp() {
        keyword1 = PopularKeyword.builder()
                .keyword("keyword1")
                .count(10)
                .build();

        keyword2 = PopularKeyword.builder()
                .keyword("keyword2")
                .count(5)
                .build();
    }

    @Test
    void saveKeyword() {
        popularKeywordService.saveKeyword(keyword1);
        verify(popularKeywordRepository, times(1)).save(keyword1);
    }

    @Test
    void findByKeyword() {
        given(popularKeywordRepository.findByKeyword("keyword1")).willReturn(Optional.of(keyword1));

        Optional<PopularKeyword> foundKeyword = popularKeywordService.findByKeyword("keyword1");
        assertEquals("keyword1", foundKeyword.orElseThrow().getKeyword());
    }

    @Test
    void top10Keyword() {
        given(popularKeywordRepository.findTop10ByOrderByCountDesc()).willReturn(Arrays.asList(keyword1, keyword2));

        List<PopularKeyword> popularKeywords = popularKeywordService.top10Keyword();
        assertEquals(2, popularKeywords.size());
        assertEquals("keyword1", popularKeywords.get(0).getKeyword());
        assertEquals("keyword2", popularKeywords.get(1).getKeyword());
    }
}