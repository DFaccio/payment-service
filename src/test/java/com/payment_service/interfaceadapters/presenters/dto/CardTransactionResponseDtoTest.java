package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTransactionResponseDtoTest {

    @Test
    public void testGettersAndSetters() {
        CardTransactionResponseDto dto = new CardTransactionResponseDto();

        dto.setPaymentId("123456");
        dto.setValue(19.99);
        dto.setCreated("2024-08-07T23:53:07.061193900");

        assertEquals("123456", dto.getPaymentId());
        assertEquals(19.99, dto.getValue());
        assertEquals("2024-08-07T23:53:07.061193900", dto.getCreated());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        CardTransactionResponseDto dto = new CardTransactionResponseDto();
        dto.setPaymentId("123456");
        dto.setValue(19.99);
        dto.setCreated("2024-08-07T23:53:07.061193900");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"id\":\"123456\""));
        assertTrue(json.contains("\"value\":19.99"));
        assertTrue(json.contains("\"created\":\"2024-08-07T23:53:07.061193900\""));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{\"id\":\"123456\",\"value\":19.99,\"created\":\"2024-08-07T23:53:07.061193900\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        CardTransactionResponseDto dto = objectMapper.readValue(json, CardTransactionResponseDto.class);

        assertEquals("123456", dto.getPaymentId());
        assertEquals(19.99, dto.getValue());
        assertEquals("2024-08-07T23:53:07.061193900", dto.getCreated());
    }

}
