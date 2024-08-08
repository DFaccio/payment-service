package com.payment_service.interfaceadapters.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.payment_service.entities.Payment;
import com.payment_service.interfaceadapters.gateway.PaymentGateway;
import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.usercase.PaymentBusiness;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.exception.ExternalInterfaceException;
import com.payment_service.util.time.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private PaymentBusiness paymentBusiness;

    @Mock
    private PaymentConverter paymentConverter;

    @InjectMocks
    private PaymentController paymentController;

    private RequestPaymentDto requestPaymentDto;
    private PaymentDto paymentDto;
    private Payment payment;

    @BeforeEach
    void setUp() {
        requestPaymentDto = new RequestPaymentDto();
        requestPaymentDto.setCpf("12345678900");
        requestPaymentDto.setCardNumber("5568872479420825");
        requestPaymentDto.setPaymentMethod("Cartão de crédito");
        requestPaymentDto.setPaymentDescription("Compra de um livro");
        requestPaymentDto.setValue(1.99);

        paymentDto = new PaymentDto();
        paymentDto.setCpf(requestPaymentDto.getCpf());
        paymentDto.setCardNumber(requestPaymentDto.getCardNumber());
        paymentDto.setPaymentMethod(requestPaymentDto.getPaymentMethod());
        paymentDto.setPaymentDescription(requestPaymentDto.getPaymentDescription());
        paymentDto.setPaymentValue(requestPaymentDto.getValue());
        paymentDto.setPaymentId(UUID.randomUUID());
        paymentDto.setTransactionDate(TimeUtils.now());
        paymentDto.setCardTransactionId("123456");
        paymentDto.setPaymentStatus(PaymentStatus.APPROVED);

        payment = new Payment();
        payment.setPaymentId(paymentDto.getPaymentId());
        payment.setCpf(paymentDto.getCpf());
        payment.setCardNumber(paymentDto.getCardNumber());
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        payment.setPaymentDescription(paymentDto.getPaymentDescription());
        payment.setPaymentValue(paymentDto.getPaymentValue());
        payment.setTransactionDate(paymentDto.getTransactionDate());
        payment.setCardTransactionId(paymentDto.getCardTransactionId());
        payment.setPaymentStatus(paymentDto.getPaymentStatus());
    }

    @Test
    public void testNewPayment_Success() throws ExternalInterfaceException, IOException {
        // Given
        when(paymentBusiness.newPayment(requestPaymentDto)).thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(paymentDto));
        when(paymentConverter.convert(paymentDto)).thenReturn(payment);
        when(paymentGateway.insert(payment)).thenReturn(payment);
        when(paymentBusiness.toResponse(payment)).thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(paymentDto));

        // When
        ResponseEntity<?> response = paymentController.newPayment(requestPaymentDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(paymentBusiness, times(1)).newPayment(requestPaymentDto);
        verify(paymentConverter, times(1)).convert(paymentDto);
        verify(paymentGateway, times(1)).insert(payment);
        verify(paymentBusiness, times(1)).toResponse(payment);
    }

    @Test
    public void testNewPayment_Failure() throws ExternalInterfaceException, IOException {
        // Given
        ResponseEntity<?> failureResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message");
        when(paymentBusiness.newPayment(requestPaymentDto)).thenReturn((ResponseEntity) failureResponse);

        // When
        ResponseEntity<?> response = paymentController.newPayment(requestPaymentDto);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error message", response.getBody());
        verify(paymentBusiness, times(1)).newPayment(requestPaymentDto);
//        verify(paymentConverter, never()).convert(any());
        verify(paymentGateway, never()).insert(any());
        verify(paymentBusiness, never()).toResponse(any());
    }

    @Test
    public void testCheckCustomerPayments_Success() throws JsonProcessingException {
        // Given
        String cpf = "12345678900";
        List<Payment> paymentList = List.of(payment);
        when(paymentGateway.checkCustomerPayments(cpf)).thenReturn(Optional.of(paymentList));
        when(paymentBusiness.checkCustomerPayments(Optional.of(paymentList)))
                .thenReturn((ResponseEntity) ResponseEntity.ok(paymentList));

        // When
        ResponseEntity<?> response = paymentController.checkCustomerPayments(cpf);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(paymentGateway, times(1)).checkCustomerPayments(cpf);
        verify(paymentBusiness, times(1)).checkCustomerPayments(Optional.of(paymentList));
    }

    @Test
    public void testCheckCustomerPayments_NoPaymentsFound() throws JsonProcessingException {
        // Given
        String cpf = "12345678900";
        when(paymentGateway.checkCustomerPayments(cpf)).thenReturn(Optional.empty());
        when(paymentBusiness.checkCustomerPayments(Optional.empty()))
                .thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.NOT_FOUND).body("No payments found"));

        // When
        ResponseEntity<?> response = paymentController.checkCustomerPayments(cpf);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No payments found", response.getBody());
        verify(paymentGateway, times(1)).checkCustomerPayments(cpf);
        verify(paymentBusiness, times(1)).checkCustomerPayments(Optional.empty());
    }
}