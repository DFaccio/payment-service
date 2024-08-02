package com.payment_service.interfaceadapters.presenters.dto;

import com.payment_service.util.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckPaymentsDto {

    private BigDecimal paymentValue;

    private String paymentDescription;

    private String paymentMethod;

    private PaymentStatus paymentStatus;

}
