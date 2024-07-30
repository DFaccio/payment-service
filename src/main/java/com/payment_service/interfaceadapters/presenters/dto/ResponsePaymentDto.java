package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class ResponsePaymentDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    private String cardNumber;

    private BigDecimal value;

    private String cpf;

    private LocalDateTime issueDate;

    private PaymentStatus status;

}
