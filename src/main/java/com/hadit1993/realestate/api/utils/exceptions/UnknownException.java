package com.hadit1993.realestate.api.utils.exceptions;

import org.springframework.http.HttpStatus;

public final class UnknownException extends HttpException {
    public UnknownException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
