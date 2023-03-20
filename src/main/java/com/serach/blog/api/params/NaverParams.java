package com.serach.blog.api.params;

import com.serach.blog.model.params.RequestParams;
import com.serach.blog.model.params.SortEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
@AllArgsConstructor
@Builder
public class NaverParams implements ParamsInterface {

    private String query;

    private String sort;

    private Integer page;

    private Integer size;

    public static NaverParams toNaverParams(RequestParams params) {
        String query = params.getUrl();
        String keyword = params.getKeyword();
        if (Strings.isNotEmpty(keyword)) {
            query = query.concat(" ").concat(keyword);
        }
        return NaverParams.builder()
                .query(query)
                .sort(changeNaverSort(params.getSort()))
                .page(params.getPage())
                .size(params.getSize())
                .build();
    }

    private static String changeNaverSort(String sortParam) {
        if (SortEnum.recency.name().equalsIgnoreCase(sortParam)) {
            sortParam = SortEnum.sim.name();
        } else {
            sortParam = SortEnum.date.name();
        }
        return sortParam;
    }
}
