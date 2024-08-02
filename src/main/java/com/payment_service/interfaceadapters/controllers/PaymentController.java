package com.payment_service.interfaceadapters.controllers;

import com.payment_service.entities.Payment;
import com.payment_service.interfaceadapters.gateways.PaymentGateway;
import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.interfaceadapters.presenters.dto.CheckPaymentsDto;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.usercase.PaymentBusiness;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class PaymentController {

    @Resource
    private PaymentGateway paymentGateway;

    @Resource
    private PaymentBusiness paymentBusiness;

    @Resource
    private PaymentConverter paymentConverter;

    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws IOException{

        ResponseEntity<?> responseEntity = paymentBusiness.newPayment(requestPaymentDto);

        Payment payment = paymentConverter.convert((PaymentDto) Objects.requireNonNull(responseEntity.getBody()));

        payment = paymentGateway.insert(payment);

        responseEntity = paymentBusiness.toResponse(payment);

        return responseEntity;

    }

    public ResponseEntity<?> checkPayments(String cpf) throws IOException{

        ResponseEntity responseEntity;
        Optional<Payment> payment;

        payment = paymentGateway.checkPayments(cpf);

        responseEntity = paymentBusiness.checkPayments(payment);

        return responseEntity;

    }

}
