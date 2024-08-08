package com.payment_service.interfaceadapters.gateway;

import com.payment_service.entities.Payment;
import com.payment_service.frameworks.db.PaymentRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentGateway {

    @Resource
    private PaymentRepository paymentRepository;

    public Payment insert(Payment payment){
        return paymentRepository.save(payment);
    }

    public Optional<List<Payment>> checkCustomerPayments(String cpf){
        return paymentRepository.findByCpf(cpf);
    };

}
