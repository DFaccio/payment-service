package com.payment_service.interfaceadapters.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.payment_service.entities.Payment;
import com.payment_service.frameworks.db.PaymentRepository;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.time.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PaymentGatewayTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentGateway paymentGateway;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setPaymentId(UUID.randomUUID());
        payment.setCpf("21910056081");
        payment.setCardNumber("5568872479420825");
        payment.setPaymentMethod("Cartão de crédito");
        payment.setPaymentDescription("Compra de um livro");
        payment.setPaymentValue(19.99);
        payment.setTransactionDate(TimeUtils.now());
        payment.setCardTransactionId("123456");
        payment.setPaymentStatus(PaymentStatus.APPROVED);
    }

    @Test
    public void testInsertPayment() {
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentGateway.insert(payment);

        assertNotNull(result);
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testCheckCustomerPayments() {
        String cpf = "21910056081";
        List<Payment> paymentList = List.of(payment);
        when(paymentRepository.findByCpf(cpf)).thenReturn(Optional.of(paymentList));

        Optional<List<Payment>> result = paymentGateway.checkCustomerPayments(cpf);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(payment.getPaymentId(), result.get().get(0).getPaymentId());
        verify(paymentRepository, times(1)).findByCpf(cpf);
    }

    @Test
    public void testCheckCustomerPaymentsWhenNoPaymentsFound() {
        String cpf = "21910056081";
        when(paymentRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        Optional<List<Payment>> result = paymentGateway.checkCustomerPayments(cpf);

        assertFalse(result.isPresent());
        verify(paymentRepository, times(1)).findByCpf(cpf);
    }
}