package com.subrutin.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestExcepton extends RuntimeException {

    public BadRequestExcepton(String message) {
        super(message);
    }

}
