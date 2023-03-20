package com.serach.blog.service;

import com.serach.blog.model.entity.PopularKeyword;
import com.serach.blog.repo.PopularKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PopularKeywordService {

    private final PopularKeywordRepository popularKeywordRepository;

    public void saveKeyword(PopularKeyword keyword) {
        popularKeywordRepository.save(keyword);
    }

    public Optional<PopularKeyword> findByKeyword(String keyword) {
        return popularKeywordRepository.findByKeyword(keyword);
    }

    public List<PopularKeyword> top10Keyword() {
        return popularKeywordRepository.findTop10ByOrderByCountDesc();
    }
}
