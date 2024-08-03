package com.payment_service.util.exception;

import com.payment_service.util.MessageUtil;

public class NotFoundException extends Exception {

    public NotFoundException(String code) {
        super(MessageUtil.getMessage(code));
    }

}