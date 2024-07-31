package com.payment_service.interfaceadapters.controllers;

import com.payment_service.frameworks.external.card.CardServiceInterface;
import com.payment_service.interfaceadapters.gateways.PaymentGateway;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.usercase.PaymentBusiness;
import com.payment_service.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PaymentController {

    @Resource
    private PaymentGateway paymentGateway;

    @Resource
    private PaymentBusiness paymentBusiness;

    @Resource
    CardServiceInterface cardServiceInterface;

    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws IOException, ValidationsException {

        ResponseEntity<?> cardResponse = cardServiceInterface.validateCard(requestPaymentDto).block();

        paymentBusiness.validateCardResponse(cardResponse);

        PaymentDto paymentDto = null;

        return cardResponse.status(HttpStatus.CREATED).body(paymentDto);

    }

}
