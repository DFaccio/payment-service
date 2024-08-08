package com.payment_service.framework.external;

import com.payment_service.frameworks.external.card.CardServiceInterface;
import com.payment_service.frameworks.external.card.CardServiceInterfaceImpl;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionResponseDto;
import static org.mockito.Mockito.when;
import com.payment_service.util.exception.ExternalInterfaceException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
class CardServiceInterfaceImplTest {

    @Mock
    private CardServiceInterface cardServiceInterface;

    @InjectMocks
    private CardServiceInterfaceImpl cardServiceInterfaceImpl;

    public CardServiceInterfaceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateCard_Success() throws ExternalInterfaceException {
        String cpf = "21910056081";
        String cardNumber = "5568872479420825";
        String expirationDate = "0625";
        String cvv = "545";

        ResponseEntity<?> expectedResponse = ResponseEntity.status(HttpStatus.OK).build();

        when(cardServiceInterfaceImpl.validateCard(cpf, cardNumber, expirationDate, cvv))
                .thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.OK).build());
//                .thenReturn((ResponseEntity) expectedResponse);

        ResponseEntity<?> response = cardServiceInterfaceImpl.validateCard(cpf, cardNumber, expirationDate, cvv);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testValidateCard_Failure() throws ExternalInterfaceException {
        String cpf = "21910056081";
        String cardNumber = "5568872479420825";
        String expirationDate = "0625";
        String cvv = "545";

        when(cardServiceInterfaceImpl.validateCard(cpf, cardNumber, expirationDate, cvv))
                .thenThrow(FeignException.class);

        assertThrows(ExternalInterfaceException.class, () -> {
            cardServiceInterfaceImpl.validateCard(cpf, cardNumber, expirationDate, cvv);
        });
    }

    @Test
    public void testNewPayment_Success() throws ExternalInterfaceException {
        CardTransactionRequestDto requestDto = new CardTransactionRequestDto();
        CardTransactionResponseDto responseDto = new CardTransactionResponseDto();

        ResponseEntity<CardTransactionResponseDto> expectedResponse = ResponseEntity.ok(responseDto);

        when(cardServiceInterfaceImpl.newPayment(requestDto))
                .thenReturn(expectedResponse);

        ResponseEntity<CardTransactionResponseDto> response = cardServiceInterfaceImpl.newPayment(requestDto);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testNewPayment_Failure() throws ExternalInterfaceException {
        CardTransactionRequestDto requestDto = new CardTransactionRequestDto();

        when(cardServiceInterfaceImpl.newPayment(requestDto))
                .thenThrow(FeignException.class);

        assertThrows(ExternalInterfaceException.class, () -> {
            cardServiceInterfaceImpl.newPayment(requestDto);
        });
    }
}
