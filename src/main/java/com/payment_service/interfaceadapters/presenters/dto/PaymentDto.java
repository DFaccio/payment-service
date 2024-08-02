package com.payment_service.interfaceadapters.presenters.dto;

import com.payment_service.util.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID paymentId;

    private String cpf;

    private String cardNumber;

    private String paymentMethod;

    private String paymentDescription;

    private BigDecimal paymentValue;

    private LocalDateTime transactionDate;

    private String cardTransactionId;

    private PaymentStatus paymentStatus;

}
