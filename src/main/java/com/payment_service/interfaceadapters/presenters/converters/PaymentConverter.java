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
        dto.setCpf(entity.getCpf());
        dto.setCardNumber(entity.getCardNumber());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentDescription(entity.getPaymentDescription());
        dto.setPaymentValue(entity.getPaymentValue());
        dto.setTransactionDate(entity.getTransactionDate());
        dto.setCardTransactionId(entity.getCardTransactionId());
        dto.setPaymentStatus(entity.getPaymentStatus());

        return dto;

    }

    public Payment convert(PaymentDto dto){

        Payment entity = new Payment();

        entity.setPaymentId(dto.getPaymentId());
        entity.setCpf(dto.getCpf());
        entity.setCardNumber(dto.getCardNumber());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentDescription(dto.getPaymentDescription());
        entity.setPaymentValue(dto.getPaymentValue());
        entity.setTransactionDate(dto.getTransactionDate());
        entity.setCardTransactionId(dto.getCardTransactionId());
        entity.setPaymentStatus(dto.getPaymentStatus());

        return entity;

    }
}
