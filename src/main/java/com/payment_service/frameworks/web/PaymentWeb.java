package com.payment_service.frameworks.web;

import com.payment_service.interfaceadapters.controllers.PaymentController;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.exception.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/v1/payment")
@Tag(name = "Payment", description = "Metódos para manipulação de Pagamento")
public class PaymentWeb {

    @Resource
    private PaymentController paymentController;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Adiciona um novo cartão")
    public ResponseEntity<?> insert(@Valid @RequestBody RequestPaymentDto dto) throws IOException, ValidationsException {

        return paymentController.newPayment(dto);

    }

}
