package com.payment_service.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class MessageUtilTest {

    @Test
    void testGetMessage() {
        String message = MessageUtil.getMessage("0101");
        assertEquals("Valor para pagamento inválido.", message);

    }

    @Test
    void testGetMessageWithValues() {
        String message = MessageUtil.getMessage("0999", "valor");
        assertEquals("Teste valor", message);

    }

    @Test
    void testGetMessageWithInvalidKey() {
        String unknown = MessageUtil.getMessage("unknown");
        assertNull(unknown, "The message for an unknown key should be null");
    }

    @Test
    void testLoadProperties() {
        String messageFilePath = "/messages.properties";
        try (InputStream fileInputStream = MessageUtil.class.getResourceAsStream(messageFilePath)) {
            assertNotNull(fileInputStream, "The properties file should be found in the test resources");
        } catch (IOException e) {
            fail("Exception occurred while accessing the properties file: " + e.getMessage());
        }
    }
}