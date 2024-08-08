package com.payment_service.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExternalInterfaceExceptionTest {

    @Test
    public void testExternalInterfaceExceptionMessage() {
        String expectedMessage = "Erro de comunicação com serviço externo";

        ExternalInterfaceException exception = new ExternalInterfaceException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testExternalInterfaceExceptionThrown() {
        String expectedMessage = "Erro de comunicação com serviço externo";

        ExternalInterfaceException exception = assertThrows(ExternalInterfaceException.class, () -> {
            throw new ExternalInterfaceException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}