package com.payment_service.util.exception;

import feign.Response;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomErrorDecoderTest {

    private CustomErrorDecoder customErrorDecoder;

    @BeforeEach
    public void setUp() {
        customErrorDecoder = new CustomErrorDecoder();
    }

    @Test
    public void testDecode_WithKnownStatus() {
        Response response = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason("Not Found")
                .request(Request.create(Request.HttpMethod.GET, "/test", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        Exception exception = customErrorDecoder.decode("testMethod", response);

        assertTrue(exception instanceof ResponseStatusException);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("Not Found", responseStatusException.getReason());
    }

    @Test
    public void testDecode_WithUnknownStatus() {
        Response response = Response.builder()
                .status(999)
                .reason("Unknown Status")
                .request(Request.create(Request.HttpMethod.GET, "/test", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        Exception exception = customErrorDecoder.decode("testMethod", response);

        assertTrue(exception instanceof ResponseStatusException);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatusException.getStatusCode());
        assertEquals("Unknown Status", responseStatusException.getReason());
    }
}

