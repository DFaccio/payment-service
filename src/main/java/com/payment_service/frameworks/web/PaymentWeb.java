package com.payment_service.frameworks.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/payment")
@Tag(name = "Payment", description = "Metódos para manipulação de Pagamento")
public class PaymentWeb {



}
