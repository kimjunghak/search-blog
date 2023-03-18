package com.serach.blog.repo;

import com.serach.blog.model.entity.PopularKeyword;

import java.util.List;
import java.util.Optional;

public interface PopularKeywordRepository extends BaseRepository<PopularKeyword, Long> {

    List<PopularKeyword> findTop10By();

    Optional<PopularKeyword> findByKeyword(String keyword);
}
