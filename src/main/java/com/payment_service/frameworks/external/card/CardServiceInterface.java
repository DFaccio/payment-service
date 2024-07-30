package com.payment_service.frameworks.external.card;

import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.enums.CardResponse;
import com.payment_service.util.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CardServiceInterface {

    CardResponse newPayment(RequestPaymentDto requestPaymentDto) throws IOException;

}
