package com.payment_service.util.exception;

import com.payment_service.util.MessageUtil;

public class CardLimitException extends Exception{

    public CardLimitException(String code) {
        super(MessageUtil.getMessage(code));
    }

}