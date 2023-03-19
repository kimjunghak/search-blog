package com.serach.blog.api.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    @JsonProperty("total_count")
    private int totalCount = 0;

    @JsonProperty("pageable_count")
    private int pageableCount = 0;

    @JsonProperty("is_end")
    private boolean isEnd = true;
}
