package com.hadit1993.realestate.api.utils.exceptions;

import org.springframework.http.HttpStatus;

public final class BadRequestException extends HttpException {
    public BadRequestException(String message, Object data) {
        super(message, HttpStatus.BAD_REQUEST, data);
    }

    public BadRequestException(Object data) {
        this("some inputs are invalid",data);
    }
}
