package com.payment_service.util.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StandardErrorTest {

    @Test
    public void testStandardErrorConstructor() {
        int statusCode = 404;
        String error = "Not Found";
        String message = "The requested resource was not found";
        String path = "/pagamentos";

        StandardError standardError = new StandardError(statusCode, error, message, path);

        assertEquals(statusCode, standardError.getStatusCode());
        assertEquals(error, standardError.getError());
        assertEquals(message, standardError.getMessage());
        assertEquals(path, standardError.getPath());
        assertNotNull(standardError.getTime());

        LocalDateTime now = LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
        assertEquals(now.getYear(), standardError.getTime().getYear());
        assertEquals(now.getMonth(), standardError.getTime().getMonth());
        assertEquals(now.getDayOfMonth(), standardError.getTime().getDayOfMonth());
    }

    @Test
    public void testStandardErrorDefaultConstructorAndSetters() {
        StandardError standardError = new StandardError();

        int statusCode = 500;
        String error = "Internal Server Error";
        String message = "An unexpected error occurred";
        String path = "/pagamentos";
        LocalDateTime time = LocalDateTime.of(2024, 8, 8, 12, 0);

        standardError.setStatusCode(statusCode);
        standardError.setError(error);
        standardError.setMessage(message);
        standardError.setPath(path);
        standardError.setTime(time);

        assertEquals(statusCode, standardError.getStatusCode());
        assertEquals(error, standardError.getError());
        assertEquals(message, standardError.getMessage());
        assertEquals(path, standardError.getPath());
        assertEquals(time, standardError.getTime());
    }
}