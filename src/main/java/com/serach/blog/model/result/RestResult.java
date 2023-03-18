package com.serach.blog.model.result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestResult {

    private boolean success;

    private String message;

    private Object data;

    public static RestResult success(Object data) {
        return new RestResult(true, data);
    }

    public static RestResult fail(String message) {
        return new RestResult(false, message);
    }

    public RestResult(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public RestResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
