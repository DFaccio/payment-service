package com.payment_service.interfaceadapters.presenters.converters;

import com.payment_service.entities.Payment;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.time.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentConverterTest {

    private PaymentConverter paymentConverter;

    @BeforeEach
    public void setUp() {
        paymentConverter = new PaymentConverter();
    }

    @Test
    public void testConvertPaymentToDto() {
        // Given
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID());
        payment.setCpf("21910056081");
        payment.setCardNumber("5568872479420825");
        payment.setPaymentMethod("Cartão de crédito");
        payment.setPaymentDescription("Compra de um livro");
        payment.setPaymentValue(19.99);
        payment.setTransactionDate(TimeUtils.now());
        payment.setCardTransactionId("123456");
        payment.setPaymentStatus(PaymentStatus.APPROVED);

        // When
        PaymentDto dto = paymentConverter.convert(payment);

        // Then
        assertNotNull(dto);
        assertEquals(payment.getPaymentId(), dto.getPaymentId());
        assertEquals(payment.getCpf(), dto.getCpf());
        assertEquals(payment.getCardNumber(), dto.getCardNumber());
        assertEquals(payment.getPaymentMethod(), dto.getPaymentMethod());
        assertEquals(payment.getPaymentDescription(), dto.getPaymentDescription());
        assertEquals(payment.getPaymentValue(), dto.getPaymentValue());
        assertEquals(payment.getTransactionDate(), dto.getTransactionDate());
        assertEquals(payment.getCardTransactionId(), dto.getCardTransactionId());
        assertEquals(payment.getPaymentStatus(), dto.getPaymentStatus());
    }

    @Test
    public void testConvertDtoToPayment() {
        PaymentDto dto = new PaymentDto();
        dto.setPaymentId(UUID.randomUUID());
        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setPaymentMethod("Cartão de crédito");
        dto.setPaymentDescription("Compra de um livro");
        dto.setPaymentValue(19.99);
        dto.setTransactionDate(LocalDateTime.now());
        dto.setCardTransactionId("123456");
        dto.setPaymentStatus(PaymentStatus.APPROVED);

        Payment payment = paymentConverter.convert(dto);

        assertNotNull(payment);
        assertEquals(dto.getPaymentId(), payment.getPaymentId());
        assertEquals(dto.getCpf(), payment.getCpf());
        assertEquals(dto.getCardNumber(), payment.getCardNumber());
        assertEquals(dto.getPaymentMethod(), payment.getPaymentMethod());
        assertEquals(dto.getPaymentDescription(), payment.getPaymentDescription());
        assertEquals(dto.getPaymentValue(), payment.getPaymentValue());
        assertEquals(dto.getTransactionDate(), payment.getTransactionDate());
        assertEquals(dto.getCardTransactionId(), payment.getCardTransactionId());
        assertEquals(dto.getPaymentStatus(), payment.getPaymentStatus());
    }
}
