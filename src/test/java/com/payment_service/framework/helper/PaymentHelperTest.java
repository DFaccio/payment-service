package com.payment_service.framework.helper;

import com.payment_service.frameworks.external.card.CardServiceInterface;
import com.payment_service.frameworks.helper.PaymentHelper;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.exception.ExternalInterfaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PaymentHelperTest {

    @Mock
    private CardServiceInterface cardServiceInterface;

    @InjectMocks
    private PaymentHelper paymentHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateCard_Success() throws ExternalInterfaceException {
        RequestPaymentDto requestPaymentDto = new RequestPaymentDto();
        requestPaymentDto.setCpf("21910056081");
        requestPaymentDto.setCardNumber("5568872479420825");
        requestPaymentDto.setExpirationDate("0625");
        requestPaymentDto.setCvv("545");

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Card is valid");
        when(cardServiceInterface.validateCard(anyString(), anyString(), anyString(), anyString()))
                .thenReturn((ResponseEntity) expectedResponse);

        ResponseEntity<?> actualResponse = paymentHelper.validateCard(requestPaymentDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testNewPayment_Success() throws ExternalInterfaceException {
        CardTransactionRequestDto cardTransactionRequestDto = new CardTransactionRequestDto();
        cardTransactionRequestDto.setCpf("21910056081");
        cardTransactionRequestDto.setCardNumber("5568872479420825");
        cardTransactionRequestDto.setExpirationDate("0625");
        cardTransactionRequestDto.setCvv("545");
        cardTransactionRequestDto.setValue(19.99);

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Payment processed successfully");
        when(cardServiceInterface.newPayment(any(CardTransactionRequestDto.class)))
                .thenReturn((ResponseEntity) expectedResponse);

        ResponseEntity<?> actualResponse = paymentHelper.newPayment(cardTransactionRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }
}
