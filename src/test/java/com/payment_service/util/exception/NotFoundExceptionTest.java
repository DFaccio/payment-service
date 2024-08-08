package com.payment_service.util.exception;

import com.payment_service.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundExceptionTest {

    private static final String TEST_CODE = "test.code";
    private static final String TEST_MESSAGE = "Test message";

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testNotFoundException_MessageRetrievedSuccessfully() {
        try (MockedStatic<MessageUtil> mockedMessageUtil = Mockito.mockStatic(MessageUtil.class)) {
            mockedMessageUtil.when(() -> MessageUtil.getMessage(TEST_CODE)).thenReturn(TEST_MESSAGE);

            NotFoundException exception = new NotFoundException(TEST_CODE);

            assertEquals(TEST_MESSAGE, exception.getMessage(), "Exception message should match the message from MessageUtil");
        }
    }
}
