package com.payment_service.util.exception;

import com.payment_service.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationsExceptionTest {

    private static final String TEST_CODE = "validation.error";
    private static final String TEST_MESSAGE = "Validation error occurred";

    @Test
    public void testValidationsException_MessageRetrievedSuccessfully() {
        try (MockedStatic<MessageUtil> mockedMessageUtil = Mockito.mockStatic(MessageUtil.class)) {
            mockedMessageUtil.when(() -> MessageUtil.getMessage(TEST_CODE)).thenReturn(TEST_MESSAGE);

            ValidationsException exception = new ValidationsException(TEST_CODE);

            assertEquals(TEST_MESSAGE, exception.getMessage(), "Exception message should match the message from MessageUtil");
        }
    }
}
