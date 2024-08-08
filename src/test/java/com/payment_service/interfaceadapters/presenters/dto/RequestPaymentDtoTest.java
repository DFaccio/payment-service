package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestPaymentDtoTest {

    @Test
    public void testGettersAndSetters() {
        RequestPaymentDto dto = new RequestPaymentDto();

        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setExpirationDate("0625");
        dto.setCvv("545");
        dto.setValue(19.99);
        dto.setPaymentDescription("Compra de um livro");
        dto.setPaymentMethod("Cartão de crédito");

        assertEquals("21910056081", dto.getCpf());
        assertEquals("5568872479420825", dto.getCardNumber());
        assertEquals("0625", dto.getExpirationDate());
        assertEquals("545", dto.getCvv());
        assertEquals(19.99, dto.getValue());
        assertEquals("Compra de um livro", dto.getPaymentDescription());
        assertEquals("Cartão de crédito", dto.getPaymentMethod());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        RequestPaymentDto dto = new RequestPaymentDto();
        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setExpirationDate("0625");
        dto.setCvv("545");
        dto.setValue(19.99);
        dto.setPaymentDescription("Compra de um livro");
        dto.setPaymentMethod("Cartão de crédito");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"cpf\":\"21910056081\""));
        assertTrue(json.contains("\"cardNumber\":\"5568872479420825\""));
        assertTrue(json.contains("\"expirationDate\":\"0625\""));
        assertTrue(json.contains("\"cvv\":\"545\""));
        assertTrue(json.contains("\"value\":19.99"));
        assertTrue(json.contains("\"paymentDescription\":\"Compra de um livro\""));
        assertTrue(json.contains("\"paymentMethod\":\"Cartão de crédito\""));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{\"cpf\":\"21910056081\",\"cardNumber\":\"5568872479420825\",\"expirationDate\":\"0625\",\"cvv\":\"545\",\"value\":19.99,\"paymentDescription\":\"Compra de um livro\",\"paymentMethod\":\"Cartão de crédito\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        RequestPaymentDto dto = objectMapper.readValue(json, RequestPaymentDto.class);

        assertEquals("21910056081", dto.getCpf());
        assertEquals("5568872479420825", dto.getCardNumber());
        assertEquals("0625", dto.getExpirationDate());
        assertEquals("545", dto.getCvv());
        assertEquals(19.99, dto.getValue());
        assertEquals("Compra de um livro", dto.getPaymentDescription());
        assertEquals("Cartão de crédito", dto.getPaymentMethod());
    }

}
