package com.serach.blog.api.params;


import com.serach.blog.model.params.RequestParams;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
@AllArgsConstructor
@Builder
public class KakaoParams implements ParamsInterface{

    private String query;

    private String sort;

    private Integer page;

    private Integer size;

    public static KakaoParams defaultKakaoParams(RequestParams params) {
        String query = params.getUrl();
        String keyword = params.getKeyword();
        if (Strings.isNotEmpty(keyword)) {
            query = query.concat(" ").concat(keyword);
        }
        return KakaoParams.builder()
                .query(query)
                .sort(params.getSort())
                .page(params.getPage())
                .size(params.getSize())
                .build();
    }
}
