package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.util.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentDtoTest {

    @Test
    public void testGettersAndSetters() {
        PaymentDto dto = new PaymentDto();

        UUID paymentId = UUID.randomUUID();
        String cpf = "21910056081";
        String cardNumber = "5568872479420825";
        String paymentMethod = "Cartão de crédito";
        String paymentDescription = "Compra de um livro";
        double paymentValue = 19.99;
        LocalDateTime transactionDate = LocalDateTime.now();
        String cardTransactionId = UUID.randomUUID().toString();
        PaymentStatus paymentStatus = PaymentStatus.APPROVED;

        dto.setPaymentId(paymentId);
        dto.setCpf(cpf);
        dto.setCardNumber(cardNumber);
        dto.setPaymentMethod(paymentMethod);
        dto.setPaymentDescription(paymentDescription);
        dto.setPaymentValue(paymentValue);
        dto.setTransactionDate(transactionDate);
        dto.setCardTransactionId(cardTransactionId);
        dto.setPaymentStatus(paymentStatus);

        assertEquals(paymentId, dto.getPaymentId());
        assertEquals(cpf, dto.getCpf());
        assertEquals(cardNumber, dto.getCardNumber());
        assertEquals(paymentMethod, dto.getPaymentMethod());
        assertEquals(paymentDescription, dto.getPaymentDescription());
        assertEquals(paymentValue, dto.getPaymentValue());
        assertEquals(transactionDate, dto.getTransactionDate());
        assertEquals(cardTransactionId, dto.getCardTransactionId());
        assertEquals(paymentStatus, dto.getPaymentStatus());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        PaymentDto dto = new PaymentDto();
        UUID paymentId = UUID.randomUUID();
        dto.setPaymentId(paymentId);
        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setPaymentMethod("Cartão de crédito");
        dto.setPaymentDescription("Compra de um livro");
        dto.setPaymentValue(19.99);
        dto.setTransactionDate(LocalDateTime.now());
        dto.setCardTransactionId(UUID.randomUUID().toString());
        dto.setPaymentStatus(PaymentStatus.APPROVED);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"paymentId\":\"" + paymentId.toString() + "\""));
        assertTrue(json.contains("\"cpf\":\"21910056081\""));
        assertTrue(json.contains("\"cardNumber\":\"5568872479420825\""));
        assertTrue(json.contains("\"paymentMethod\":\"Cartão de crédito\""));
        assertTrue(json.contains("\"paymentDescription\":\"Compra de um livro\""));
        assertTrue(json.contains("\"paymentValue\":19.99"));
        assertTrue(json.contains("\"cardTransactionId\""));
        assertTrue(json.contains("\"paymentStatus\":\"APPROVED\""));
        assertTrue(json.contains("\"transactionDate\""));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        UUID paymentId = UUID.randomUUID();
        String json = "{\"paymentId\":\"" + paymentId.toString() + "\",\"cpf\":\"21910056081\",\"cardNumber\":\"5568872479420825\",\"paymentMethod\":\"Cartão de crédito\",\"paymentDescription\":\"Compra de um livro\",\"paymentValue\":19.99,\"transactionDate\":\"2024-08-08T10:15:30\",\"cardTransactionId\":\"123456\",\"paymentStatus\":\"APPROVED\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        PaymentDto dto = objectMapper.readValue(json, PaymentDto.class);

        assertEquals(paymentId, dto.getPaymentId());
        assertEquals("21910056081", dto.getCpf());
        assertEquals("5568872479420825", dto.getCardNumber());
        assertEquals("Cartão de crédito", dto.getPaymentMethod());
        assertEquals("Compra de um livro", dto.getPaymentDescription());
        assertEquals(19.99, dto.getPaymentValue());
        assertEquals(LocalDateTime.parse("2024-08-08T10:15:30"), dto.getTransactionDate());
        assertEquals("123456", dto.getCardTransactionId());
        assertEquals(PaymentStatus.APPROVED, dto.getPaymentStatus());
    }

}
