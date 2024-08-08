package com.payment_service.frameworks.external.card;

import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionResponseDto;
import com.payment_service.util.config.FeignClientConfig;
import com.payment_service.util.exception.CustomErrorDecoder;
import com.payment_service.util.exception.ExternalInterfaceException;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartaoService", url = "${card.feign}", configuration ={ FeignClientConfig.class, CustomErrorDecoder.class})
public interface CardServiceInterface {

    @GetMapping(value = "/cartao", headers = "Content-Type=application/json")
    ResponseEntity<?> validateCard(@RequestParam String cpf,
                                   @RequestParam String numero,
                                   @RequestParam String data,
                                   @RequestParam String cvv) throws ExternalInterfaceException;

    @PostMapping(value = "/cartao/transactions", headers = "Content-Type=application/json")
    ResponseEntity<CardTransactionResponseDto> newPayment(@RequestBody @Valid CardTransactionRequestDto cardTransactionRequestDto ) throws ExternalInterfaceException;

}
