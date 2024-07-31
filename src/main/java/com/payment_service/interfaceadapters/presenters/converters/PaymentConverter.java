package com.payment_service.interfaceadapters.presenters.converters;

import com.payment_service.entities.Payment;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {

    @Resource
    private CardConverter cardConverter;

    public PaymentDto convert(Payment entity){

        PaymentDto dto = new PaymentDto();

        dto.setPaymentId(entity.getPaymentId());
        dto.setCardTransactionId(entity.getCardTransactionId());
        dto.setTransactionDate(entity.getTransactionDate());
        dto.setPaymentValue(entity.getPaymentValue());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setCardInformation(cardConverter.convert(entity.getCardInformation()));

        return dto;

    }

    public Payment convert(PaymentDto dto){

        Payment entity = new Payment();

        entity.setPaymentId(dto.getPaymentId());
        entity.setCardTransactionId(dto.getCardTransactionId());
        entity.setTransactionDate(dto.getTransactionDate());
        entity.setPaymentValue(dto.getPaymentValue());
        entity.setPaymentStatus(dto.getPaymentStatus());
        entity.setCardInformation(cardConverter.convert(dto.getCardInformation()));

        return entity;

    }
}
