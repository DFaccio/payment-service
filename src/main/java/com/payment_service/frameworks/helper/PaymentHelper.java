package com.payment_service.frameworks.helper;

import com.payment_service.frameworks.external.card.CardServiceInterface;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PaymentHelper {

    @Resource
    private CardServiceInterface cardServiceInterface;

    public ResponseEntity<?> validateCard(RequestPaymentDto requestPaymentDto) throws IOException {

        return cardServiceInterface.validateCard(requestPaymentDto);

    }

    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws IOException {

        return cardServiceInterface.newPayment(requestPaymentDto);

    }


}
