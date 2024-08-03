package com.payment_service.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerUtil extends Throwable {

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> cardValidationError(ValidationsException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(status.value(),
                "Data Error",
                e.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> cardLimitError(ValidationsException e) {
        HttpStatus status = HttpStatus.PAYMENT_REQUIRED;

        StandardError error = new StandardError(status.value(),
                "Limit error",
                e.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> PaymentsNotFound(ValidationsException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(status.value(),
                "Not found",
                e.getMessage());

        return ResponseEntity.status(status).body(error);
    }

}