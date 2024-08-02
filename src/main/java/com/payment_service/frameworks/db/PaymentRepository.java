package com.payment_service.frameworks.db;

import com.payment_service.entities.Payment;
import com.payment_service.util.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findById(UUID paymentId);

    Optional<Payment> findByCpf(String cpf);

    Page<Payment> findAll(Pageable pageable);

    Page<Payment> findByStatus(Pageable pageable, PaymentStatus status);

    Page<Payment> findAllByCpfAndStatus(Pageable pageable, String cpf, PaymentStatus paymentStatus);

    Page<Payment> findAllByCpf(Pageable pageable, String cpf);

}