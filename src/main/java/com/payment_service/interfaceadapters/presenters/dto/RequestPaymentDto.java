package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestPaymentDto {

    @Schema(description = "CPF do portador do cartão", example = "21910056081")
    private String cpf;

    @Schema(description = "Número do cartão", example = "5568872479420825")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])((\\d{2}|\\d{4}))$")
    @Schema(description = "Data de expiração do cartão", example = "0625")
    private String expirationDate;

    @Pattern(regexp = "^([0-9]{3})$")
    @Schema(description = "CVV do cartão", example = "545")
    private String cvv;

    @Schema(description = "Valor do lançamento", example = "1.99")
    private double value;

    @Schema(description = "Descrição do pagamento", example = "Compra de um livro")
    private String paymentDescription;

    @Schema(description = "Método do pagamento", example = "Cartão de crédito")
    private String paymentMethod;

}
