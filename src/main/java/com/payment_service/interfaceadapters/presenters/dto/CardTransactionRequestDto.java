package com.payment_service.interfaceadapters.presenters.dto;

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

    private String cpf;

    private String cardNumber;

    private String expirationDate;

    private String cvv;

    private BigDecimal value;

}
