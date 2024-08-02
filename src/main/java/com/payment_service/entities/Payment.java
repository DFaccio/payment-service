package com.payment_service.entities;

import com.payment_service.util.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "payment")
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private UUID paymentId;

    @Column
    private String cpf;

    @Column
    private String cardNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_description")
    private String paymentDescription;

    @Column(name = "payment_value", precision = 15, scale = 4)
    private BigDecimal paymentValue;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    @Column(name = "card_transaction_id")
    private String cardTransactionId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

}
