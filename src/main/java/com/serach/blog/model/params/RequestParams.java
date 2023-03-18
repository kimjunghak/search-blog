package com.serach.blog.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestParams {

    @NotNull(message = "url must not null")
    private String url;

    private String keyword;

    private String sort = SortEnum.accuracy.toString();

    @Range(min = 1, max = 50, message = "1 <= page <= 50")
    private int page = 1;

    @Range(min = 1, max = 50, message = "1 <= size <= 50")
    private int size = 10;


}
