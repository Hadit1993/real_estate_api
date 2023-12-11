package com.hadit1993.realestate.api.utils.exceptions;

import org.springframework.http.HttpStatus;

public final class UnAuthorizedException extends HttpException {
    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, null);
    }
}
