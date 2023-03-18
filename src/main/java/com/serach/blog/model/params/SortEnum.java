package com.serach.blog.model.params;

import java.util.Arrays;

public enum SortEnum {
    accuracy,
    recency;

    public static void valid(String sort) {
        Arrays.stream(SortEnum.values())
                .filter(sortEnum -> sortEnum.name().equals(sort))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not support sort string"));
    }
}
