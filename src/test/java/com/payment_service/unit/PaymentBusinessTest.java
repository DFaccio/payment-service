package com.payment_service.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.payment_service.entities.Payment;
import com.payment_service.frameworks.helper.PaymentHelper;
import com.payment_service.interfaceadapters.gateways.PaymentGateway;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionResponseDto;
import com.payment_service.interfaceadapters.presenters.dto.CheckPaymentsDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.usercase.PaymentBusiness;
import com.payment_service.util.MessageUtil;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.exception.ExceptionHandlerUtil;
import com.payment_service.util.exception.ExternalInterfaceException;
import com.payment_service.util.exception.StandardError;
import com.payment_service.util.time.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentBusinessTest{

    @InjectMocks
    private PaymentBusiness paymentBusiness;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private ExceptionHandlerUtil exceptionHandlerUtil;

    @Mock
    private PaymentHelper paymentHelper;

    private Payment payment;

    private List<Payment> paymentList;

    private RequestPaymentDto requestPaymentDto;

    private CardTransactionRequestDto cardTransactionRequestDto;

    private CardTransactionResponseDto cardTransactionResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = newPayment();
        paymentList = new ArrayList<>();
        paymentList.add(payment);
        requestPaymentDto = newRequestPaymentDto();
        cardTransactionRequestDto = newCardTransactionRequestDto();
        cardTransactionResponseDto = newCardTransactionResponseDto();

    }

    @Test
    void newPaymentTest(){



    }

//    @Test
//    public void testNewPaymentWithInvalidValue() throws ExternalInterfaceException, IOException {
//        validRequest.setValue(0);
//
//        ResponseEntity<?> response = paymentService.newPayment(validRequest);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("0101", ((ValidationsException) Objects.requireNonNull(response.getBody())).getMessage());
//    }

    @Test
    void checkCustomerPaymentsTest() throws JsonProcessingException {

        Optional<List<Payment>> optionalPayments = Optional.of(paymentList);

        ResponseEntity<?> response = paymentBusiness.checkCustomerPayments(optionalPayments);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<CheckPaymentsDto> checkPaymentsDtoList = (List<CheckPaymentsDto>) response.getBody();
        assertEquals(1, checkPaymentsDtoList.size());
        assertEquals(UUID.fromString("fcb829c9-f4f0-43b2-8647-8052806ba0b5"), checkPaymentsDtoList.get(0).getPaymentId());

    }

    @Test
    public void testCheckCustomerPaymentsWithEmptyList() throws JsonProcessingException {
        Optional<List<Payment>> optionalPayments = Optional.of(new ArrayList<>());

        ResponseEntity<?> response = paymentBusiness.checkCustomerPayments(optionalPayments);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<CheckPaymentsDto> checkPaymentsDtoList = (List<CheckPaymentsDto>) response.getBody();
        assertEquals(0, checkPaymentsDtoList.size());
    }

    @Test
    public void testCheckCustomerPaymentsWithoutPayments() throws JsonProcessingException {
        Optional<List<Payment>> optionalPayments = Optional.empty();

        ResponseEntity<?> response = paymentBusiness.checkCustomerPayments(optionalPayments);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        StandardError exception = (StandardError) response.getBody();
        assertEquals(MessageUtil.getMessage("0301"), exception.getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidValue() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setValue(0);

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0101"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidCvv() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setCvv("");

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0102"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidExpirationDate() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setExpirationDate("");

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0103"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidCpf() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setCpf("");

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0104"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidCardNumber() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setCardNumber("");

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0105"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidPaymentDescription() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setPaymentDescription("");

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0106"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithInvalidPaymentMethod() throws ExternalInterfaceException, IOException {
        requestPaymentDto.setPaymentMethod("");

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MessageUtil.getMessage("0107"), ((StandardError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void testNewPaymentWithValidRequest() throws ExternalInterfaceException, IOException {

        when(paymentHelper.validateCard(any(RequestPaymentDto.class))).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        // Casting do tipo ResponseEntity<?> para ResponseEntity<CardTransactionResponseDto>
        when(paymentHelper.newPayment(any(CardTransactionRequestDto.class)))
                .thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(cardTransactionResponseDto));

        ResponseEntity<?> response = paymentBusiness.newPayment(requestPaymentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    private Payment newPayment(){

        Payment entity = new Payment();

        entity.setPaymentId(UUID.fromString("fcb829c9-f4f0-43b2-8647-8052806ba0b5"));
        entity.setCpf("21910056081");
        entity.setCardNumber("5568872479420825");
        entity.setPaymentMethod("Cartão de crédito");
        entity.setPaymentDescription("Compra de um livro");
        entity.setPaymentValue(150.00);
        entity.setTransactionDate(TimeUtils.now());
        entity.setCardTransactionId("123456");
        entity.setPaymentStatus(PaymentStatus.APPROVED);

        return entity;

    }

    private CheckPaymentsDto checkPaymentsDto(){

        CheckPaymentsDto checkPaymentsDto = new CheckPaymentsDto();

        checkPaymentsDto.setPaymentValue(150.00);
        checkPaymentsDto.setPaymentMethod("Cartão de crédito");
        checkPaymentsDto.setPaymentDescription("Compra de um livro");
        checkPaymentsDto.setPaymentStatus(PaymentStatus.APPROVED);

        return checkPaymentsDto;

    }

    private RequestPaymentDto newRequestPaymentDto(){

        RequestPaymentDto requestPaymentDto = new RequestPaymentDto();

        requestPaymentDto.setCardNumber("5568872479420825");
        requestPaymentDto.setCpf("21910056081");
        requestPaymentDto.setExpirationDate("0625");
        requestPaymentDto.setCvv("545");
        requestPaymentDto.setValue(19.99);
        requestPaymentDto.setPaymentMethod("Cartão de crédito");
        requestPaymentDto.setPaymentDescription("Compra de um livro");

        return requestPaymentDto;

    }

    private CardTransactionRequestDto newCardTransactionRequestDto(){
        CardTransactionRequestDto cardTransactionRequestDto = new CardTransactionRequestDto();

        cardTransactionRequestDto.setCardNumber("5568872479420825");
        cardTransactionRequestDto.setCpf("21910056081");
        cardTransactionRequestDto.setExpirationDate("0625");
        cardTransactionRequestDto.setCvv("545");
        cardTransactionRequestDto.setValue(19.99);

        return cardTransactionRequestDto;

    }

    private CardTransactionResponseDto newCardTransactionResponseDto(){
        CardTransactionResponseDto cardTransactionResponseDto = new CardTransactionResponseDto();

        cardTransactionResponseDto.setPaymentId("123456");
        cardTransactionResponseDto.setValue(19.99);
        cardTransactionResponseDto.setCreated(TimeUtils.now().toString());

        return cardTransactionResponseDto;

    }

}
