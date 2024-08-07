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
    @Column
    private UUID paymentId;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "cardnumber")
    private String cardNumber;

    @Column(name = "paymentmethod")
    private String paymentMethod;

    @Column(name = "paymentdescription")
    private String paymentDescription;

    @Column(name = "paymentvalue", precision = 15, scale = 4)
    private BigDecimal paymentValue;

    @Column(name = "transactiondate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    @Column(name = "cardtransactionid")
    private String cardTransactionId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "paymentstatus")
    private PaymentStatus paymentStatus;

}
