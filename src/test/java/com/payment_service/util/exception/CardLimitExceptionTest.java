package com.payment_service.util.exception;

import com.payment_service.util.MessageUtil;
import com.payment_service.util.enums.CardResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class CardLimitExceptionTest {

    @Test
    void testCardLimitException_MessageRetrievedSuccessfully() {
        String errorCode = CardResponse.INSUFFICIENT_CARD_LIMIT.toString();
        String expectedMessage = "Pagamento recusado. Limite indisponível.";

        try (MockedStatic<MessageUtil> mockedMessageUtil = mockStatic(MessageUtil.class)) {
            mockedMessageUtil.when(() -> MessageUtil.getMessage(errorCode)).thenReturn(expectedMessage);

            CardLimitException exception = new CardLimitException(errorCode);

            assertEquals(expectedMessage, exception.getMessage());
        }
    }

}
