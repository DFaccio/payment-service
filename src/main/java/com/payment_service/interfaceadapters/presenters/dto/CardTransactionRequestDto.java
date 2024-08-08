package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardTransactionRequestDto {

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("numero")
    private String cardNumber;

    @JsonProperty("data_validade")
    private String expirationDate;

    @JsonProperty("cvv")
    private String cvv;

    @JsonProperty("valor")
    private double value;

}
