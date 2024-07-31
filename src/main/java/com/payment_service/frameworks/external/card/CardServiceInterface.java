package com.payment_service.frameworks.external.card;

import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public interface CardServiceInterface {

    Mono<ResponseEntity> validateCard(RequestPaymentDto requestPaymentDto) throws IOException;

    Mono<ResponseEntity> newPayment(RequestPaymentDto requestPaymentDto) throws IOException;

}
