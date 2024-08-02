package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @NotBlank
    @Schema(description = "CPF do portador do cartão", example = "43556112566")
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    @Schema(description = "Número do cartão", example = "5568872479420825")
    @Size(min = 16, max = 16)
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])((\\d{2}|\\d{4}))$")
    @Schema(description = "Data de expiração do cartão", example = "0625")
    @Size(min = 4, max = 6)
    private String expirationDate;

    @NotBlank
    @Pattern(regexp = "^([0-9]{3})$")
    @Schema(description = "CVV do cartão", example = "545")
    @Size(min = 3, max = 4)
    private String cvv;

    @NotNull
    @Schema(description = "Valor do lançamento", example = "100.00")
    private BigDecimal value;

    @NotBlank
    @Schema(description = "Descrição do pagamento", example = "Compra de um livro")
    private String paymentDescription;

    @NotBlank
    @Schema(description = "Método do pagamento", example = "Cartão de crédito")
    private String paymentMethod;

}
