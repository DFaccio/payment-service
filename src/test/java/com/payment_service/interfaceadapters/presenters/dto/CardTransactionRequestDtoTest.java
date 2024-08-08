package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTransactionRequestDtoTest {

    @Test
    public void testGettersAndSetters() {
        CardTransactionRequestDto dto = new CardTransactionRequestDto();

        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setExpirationDate("0625");
        dto.setCvv("545");
        dto.setValue(19.99);

        assertEquals("21910056081", dto.getCpf());
        assertEquals("5568872479420825", dto.getCardNumber());
        assertEquals("0625", dto.getExpirationDate());
        assertEquals("545", dto.getCvv());
        assertEquals(19.99, dto.getValue());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        CardTransactionRequestDto dto = new CardTransactionRequestDto();
        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setExpirationDate("0625");
        dto.setCvv("545");
        dto.setValue(19.99);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"cpf\":\"21910056081\""));
        assertTrue(json.contains("\"numero\":\"5568872479420825\""));
        assertTrue(json.contains("\"data_validade\":\"0625\""));
        assertTrue(json.contains("\"cvv\":\"545\""));
        assertTrue(json.contains("\"valor\":19.99"));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{\"cpf\":\"21910056081\",\"numero\":\"5568872479420825\",\"data_validade\":\"0625\",\"cvv\":\"545\",\"valor\":19.99}";

        ObjectMapper objectMapper = new ObjectMapper();

        CardTransactionRequestDto dto = objectMapper.readValue(json, CardTransactionRequestDto.class);

        assertEquals("21910056081", dto.getCpf());
        assertEquals("5568872479420825", dto.getCardNumber());
        assertEquals("0625", dto.getExpirationDate());
        assertEquals("545", dto.getCvv());
        assertEquals(19.99, dto.getValue());
    }

}
