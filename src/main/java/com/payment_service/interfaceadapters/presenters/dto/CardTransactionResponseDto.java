package com.payment_service.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardTransactionResponseDto {

    @JsonProperty("id")
    private String paymentId;

    @JsonProperty("value")
    private double value;

    @JsonProperty("created")
    private String created;

}
