package com.payment_service.util.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.resolve(response.status());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

//        if (status != HttpStatus.INTERNAL_SERVER_ERROR) {
//            return new RuntimeException(response.reason());
//        }

        return new ResponseStatusException(status, response.reason());
    }
}