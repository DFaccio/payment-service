package com.payment_service.util.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.util.enums.CardResponse;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionHandlerUtilTest {

    private final ExceptionHandlerUtil exceptionHandlerUtil = new ExceptionHandlerUtil();

    @Test
    public void testCardValidationError() {
        ValidationsException exception = new ValidationsException("0201");

        ResponseEntity<StandardError> response = exceptionHandlerUtil.cardValidationError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Data Error", response.getBody().getError());
        assertEquals("Pagamento recusado. Dados do cartão inválidos.", response.getBody().getMessage());
    }

    @Test
    public void testCardLimitError() {
        CardLimitException exception = new CardLimitException("0203");

        ResponseEntity<StandardError> response = exceptionHandlerUtil.cardLimitError(exception);

        assertEquals(HttpStatus.PAYMENT_REQUIRED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Limit error", response.getBody().getError());
        assertEquals("Pagamento recusado. Limite indisponível.", response.getBody().getMessage());
    }

    @Test
    public void testPaymentsNotFound() throws JsonProcessingException {
        NotFoundException exception = new NotFoundException("0301");

        ResponseEntity<StandardError> response = exceptionHandlerUtil.paymentsNotFound(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not found", response.getBody().getError());
        assertEquals("Nenhum pagamento encontrado para o cliente.", response.getBody().getMessage());
    }

    @Test
    public void testHandleFeignExceptionValidation() throws JsonProcessingException {
        FeignException exception = Mockito.mock(FeignException.class);
        ObjectMapper objectMapper = new ObjectMapper();
        StandardError error = new StandardError();
        error.setError(CardResponse.UNREPORTED_DATA_ERROR.toString());
        Mockito.when(exception.contentUTF8()).thenReturn(objectMapper.writeValueAsString(error));

        ResponseEntity<?> response = exceptionHandlerUtil.handleFeignException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((ResponseEntity<?>) response).getStatusCode());
        assertNotNull(((ResponseEntity<?>) response).getBody());
    }

    @Test
    public void testHandleFeignExceptionNotFound() throws JsonProcessingException {
        FeignException exception = Mockito.mock(FeignException.class);
        ObjectMapper objectMapper = new ObjectMapper();
        StandardError error = new StandardError();
        error.setError(CardResponse.CARD_NOT_FOUND.toString());
        Mockito.when(exception.contentUTF8()).thenReturn(objectMapper.writeValueAsString(error));

        ResponseEntity<?> response = exceptionHandlerUtil.handleFeignException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((ResponseEntity<?>) response).getStatusCode());
        assertNotNull(((ResponseEntity<?>) response).getBody());
    }

    @Test
    public void testHandleFeignExceptionLimit() throws JsonProcessingException {
        FeignException exception = Mockito.mock(FeignException.class);
        ObjectMapper objectMapper = new ObjectMapper();
        StandardError error = new StandardError();
        error.setError(CardResponse.INSUFFICIENT_CARD_LIMIT.toString());
        Mockito.when(exception.contentUTF8()).thenReturn(objectMapper.writeValueAsString(error));

        ResponseEntity<?> response = exceptionHandlerUtil.handleFeignException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.PAYMENT_REQUIRED, ((ResponseEntity<?>) response).getStatusCode());
        assertNotNull(((ResponseEntity<?>) response).getBody());
    }

}