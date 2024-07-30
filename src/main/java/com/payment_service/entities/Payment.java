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
    private UUID id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "value", precision = 15, scale = 4)
    private BigDecimal value;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime issueDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

}
