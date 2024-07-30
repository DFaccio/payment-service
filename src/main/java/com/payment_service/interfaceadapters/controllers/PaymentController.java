package com.payment_service.interfaceadapters.controllers;

import com.payment_service.interfaceadapters.gateways.PaymentGateway;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.ResponsePaymentDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PaymentController {

    @Resource
    private PaymentGateway paymentGateway;

    public ResponsePaymentDto newPayment(RequestPaymentDto requestPaymentDto){

        return null;

    }

}
