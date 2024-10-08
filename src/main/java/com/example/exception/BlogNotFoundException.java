package com.example.exception;

import org.springframework.http.HttpStatus;

public class BlogNotFoundException extends RuntimeException implements BaseHttpException{
    public BlogNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
