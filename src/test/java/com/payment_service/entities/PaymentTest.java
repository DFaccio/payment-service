package com.payment_service.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.time.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class PaymentTest {

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = new Payment();
    }

    @Test
    public void testGettersAndSetters() {
        UUID uuid = UUID.randomUUID();
        payment.setPaymentId(uuid);
        payment.setCpf("21910056081");
        payment.setCardNumber("5568872479420825");
        payment.setPaymentMethod("Cartão de crédito");
        payment.setPaymentDescription("Compra de um livro");
        payment.setPaymentValue(19.99);
        payment.setTransactionDate(TimeUtils.now());
        payment.setCardTransactionId("123456");
        payment.setPaymentStatus(PaymentStatus.APPROVED);

        assertThat(payment.getPaymentId()).isEqualTo(uuid);
        assertThat(payment.getCpf()).isEqualTo("21910056081");
        assertThat(payment.getCardNumber()).isEqualTo("5568872479420825");
        assertThat(payment.getPaymentMethod()).isEqualTo("Cartão de crédito");
        assertThat(payment.getPaymentDescription()).isEqualTo("Compra de um livro");
        assertThat(payment.getPaymentValue()).isEqualTo(19.99);
        assertThat(payment.getTransactionDate()).isNotNull();
        assertThat(payment.getCardTransactionId()).isEqualTo("123456");
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.APPROVED);
    }

    @Test
    public void testGenerateUUID() {
        Payment payment = new Payment();

        payment.generateUUID();

        assertNotNull(payment.getPaymentId(), "Payment ID should be generated");
    }

    @Test
    public void testGenerateUUIDWhenUUIDAlreadyPresent() {
        UUID existingId = UUID.randomUUID();
        Payment payment = new Payment();
        payment.setPaymentId(existingId);

        payment.generateUUID();

        assertThat(payment.getPaymentId()).isEqualTo(existingId);
    }

}
