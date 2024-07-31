package com.payment_service.interfaceadapters.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardDto {

    private String cardNumber;

    private String cpf;

}
