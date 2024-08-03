package com.payment_service.frameworks.external.card;

import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CardServiceInterface {

    ResponseEntity<?> validateCard(RequestPaymentDto requestPaymentDto) throws IOException;

    ResponseEntity<?> newPayment(CardTransactionRequestDto cardTransactionRequestDto) throws IOException;

}
