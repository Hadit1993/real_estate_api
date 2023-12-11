package com.hadit1993.realestate.api.utils.handlers;



import com.hadit1993.realestate.api.utils.exceptions.HttpException;
import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ResponseTemplate<Object>> handleHttpException(HttpException e) {
        return new ResponseTemplate<>(e.getData(), false, e.getMessage()).convertToResponse(e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseTemplate<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        String message = !errors.isEmpty() ? "some inputs are invalid." : ex.getMessage();
        ResponseTemplate<Object> response = new ResponseTemplate<>(errors, false, message);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseTemplate<Object>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            String[] propertyPath = constraintViolation.getPropertyPath().toString().split("\\.");
            errors.put(propertyPath[propertyPath.length - 1], constraintViolation.getMessage());

        });


        return new ResponseTemplate<Object>(errors, false, "some inputs are invalid.").convertToResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseTemplate<Object>> handleMediaTypeNotSupported() {
        return new ResponseTemplate<>(null, false, "media type is not supported.")
                .convertToResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseTemplate<Object>> handleMessageNotReadable(HttpMessageNotReadableException exception) {
        return new ResponseTemplate<>(null, false, exception.getMessage())
                .convertToResponse(HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseTemplate<Object>> handleNoResourceFound(NoResourceFoundException exception) {
        return new ResponseTemplate<>(null, false, "The route " + exception.getResourcePath() + " not found.")
                .convertToResponse(HttpStatus.NOT_FOUND);

    }

}
