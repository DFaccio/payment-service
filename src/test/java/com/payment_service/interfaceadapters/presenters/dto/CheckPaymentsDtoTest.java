package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.util.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckPaymentsDtoTest {

    @Test
    public void testGettersAndSetters() {
        CheckPaymentsDto dto = new CheckPaymentsDto();

        UUID paymentId = UUID.randomUUID();
        dto.setPaymentId(paymentId);
        dto.setPaymentValue(19.99);
        dto.setPaymentDescription("Compra de um livro");
        dto.setPaymentMethod("Cartão de crédito");
        dto.setPaymentStatus(PaymentStatus.APPROVED);

        assertEquals(paymentId, dto.getPaymentId());
        assertEquals(19.99, dto.getPaymentValue());
        assertEquals("Compra de um livro", dto.getPaymentDescription());
        assertEquals("Cartão de crédito", dto.getPaymentMethod());
        assertEquals(PaymentStatus.APPROVED, dto.getPaymentStatus());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        CheckPaymentsDto dto = new CheckPaymentsDto();
        UUID paymentId = UUID.randomUUID();
        dto.setPaymentId(paymentId);
        dto.setPaymentValue(19.99);
        dto.setPaymentDescription("Compra de um livro");
        dto.setPaymentMethod("Cartão de crédito");
        dto.setPaymentStatus(PaymentStatus.APPROVED);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"paymentId\":\"" + paymentId.toString() + "\""));
        assertTrue(json.contains("\"paymentValue\":19.99"));
        assertTrue(json.contains("\"paymentDescription\":\"Compra de um livro\""));
        assertTrue(json.contains("\"paymentMethod\":\"Cartão de crédito\""));
        assertTrue(json.contains("\"paymentStatus\":\"APPROVED\""));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        UUID paymentId = UUID.randomUUID();
        String json = "{\"paymentId\":\"" + paymentId.toString() + "\",\"paymentValue\":19.99,\"paymentDescription\":\"Compra de um livro\",\"paymentMethod\":\"Cartão de crédito\",\"paymentStatus\":\"APPROVED\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        CheckPaymentsDto dto = objectMapper.readValue(json, CheckPaymentsDto.class);

        assertEquals(paymentId, dto.getPaymentId());
        assertEquals(19.99, dto.getPaymentValue());
        assertEquals("Compra de um livro", dto.getPaymentDescription());
        assertEquals("Cartão de crédito", dto.getPaymentMethod());
        assertEquals(PaymentStatus.APPROVED, dto.getPaymentStatus());
    }

}
