package com.serach.blog.config;

import com.serach.blog.model.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    RestResult exception(Exception e) {
        log.error("[Exception Error Occur!!] cause: {}", e.getMessage());
        return RestResult.fail(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestResult illegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException Error Occur!!] cause: {}", e.getMessage());
        return RestResult.fail(e.getMessage());
    }
}
