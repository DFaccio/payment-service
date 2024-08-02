package com.payment_service.interfaceadapters.gateways;

import com.payment_service.entities.Payment;
import com.payment_service.frameworks.db.PaymentRepository;
import com.payment_service.util.enums.PaymentStatus;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentGateway {

    @Resource
    private PaymentRepository paymentRepository;

    public Payment insert(Payment payment){
        return paymentRepository.save(payment);
    }

    public Optional<Payment> findById(UUID paymentId){
        return paymentRepository.findById(paymentId);
    }

    public Page<Payment> findAll(Pageable pageable){
        return paymentRepository.findAll(pageable);
    }

    public Page<Payment> findByStatus(Pageable pageable, PaymentStatus status){
        return paymentRepository.findByStatus(pageable, status);
    }

    public Page<Payment> findAllByCpfAndStatus(Pageable pageable, String cpf, PaymentStatus paymentStatus){
        return paymentRepository.findAllByCpfAndStatus(pageable, cpf, paymentStatus);
    };

    public Page<Payment> findAllByCpf(Pageable pageable, String cpf){
        return paymentRepository.findAllByCpf(pageable, cpf);
    };

    public Optional<Payment> checkPayments(String cpf){
        return paymentRepository.findByCpf(cpf);
    };

}
