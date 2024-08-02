package com.payment_service.frameworks.web;

import com.payment_service.interfaceadapters.controllers.PaymentController;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/v1/payment")
@Tag(name = "Payment", description = "Metódos para manipulação de Pagamento")
public class PaymentWeb {

    @Resource
    private PaymentController paymentController;

    @PostMapping(value="/newPayment", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Realiza um novo pagamento")
    public ResponseEntity<?> newPayment(@Valid @RequestBody RequestPaymentDto dto) throws IOException{

        return paymentController.newPayment(dto);

    }
    @PostMapping(value="/checkPayments/{cpf}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Consulta todos os pagamentos realizados para um determinado CPF")
    public ResponseEntity<?> checkPayments(@PathVariable String cpf) throws IOException{

        return paymentController.checkPayments(cpf);

    }

}
