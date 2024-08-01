package com.payment_service.util.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerUtil extends Throwable {

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> validationsError(ValidationsException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(status.value(),
                e.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> cardLimitError(ValidationsException e) {
        HttpStatus status = HttpStatus.PAYMENT_REQUIRED;

        StandardError error = new StandardError(status.value(),
                e.getMessage());

        return ResponseEntity.status(status).body(error);
    }

}