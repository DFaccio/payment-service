package com.payment_service.frameworks.web;

import com.payment_service.interfaceadapters.controllers.PaymentController;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.exception.ExternalInterfaceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/pagamentos")
@Tag(name = "Pagamentos", description = "Metódos para manipulação de Pagamento")
public class PaymentWeb {

    @Resource
    private PaymentController paymentController;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Realiza um novo pagamento")
    public ResponseEntity<?> newPayment(@Valid @RequestBody RequestPaymentDto dto) throws ExternalInterfaceException {

        return paymentController.newPayment(dto);

    }
    @GetMapping(value="/cliente/{cpf}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Consulta todos os pagamentos realizados para um determinado CPF")
    public ResponseEntity<?> checkCustomerPayments(@PathVariable String cpf) {

        return paymentController.checkCustomerPayments(cpf);

    }

}
