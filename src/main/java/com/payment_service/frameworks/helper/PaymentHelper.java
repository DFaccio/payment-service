package com.payment_service.frameworks.helper;

import com.payment_service.frameworks.external.card.CardServiceInterface;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.exception.ExternalInterfaceException;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentHelper {

    @Resource
    private CardServiceInterface cardServiceInterface;

    public ResponseEntity<?> validateCard(RequestPaymentDto requestPaymentDto) throws ExternalInterfaceException {

        String cpf = requestPaymentDto.getCpf();
        String cardNumber = requestPaymentDto.getCardNumber();
        String expirationDate = requestPaymentDto.getExpirationDate();
        String cvv = requestPaymentDto.getCvv();

        return cardServiceInterface.validateCard(cpf, cardNumber, expirationDate, cvv);

    }

    public ResponseEntity<?> newPayment(CardTransactionRequestDto cardTransactionRequestDto) throws ExternalInterfaceException {

        return cardServiceInterface.newPayment(cardTransactionRequestDto);

    }

}
