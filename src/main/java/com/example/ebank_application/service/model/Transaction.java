package com.example.ebank_application.service.model;

import com.example.ebank_application.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // DEBIT / CREDIT

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String label; // ex: "Virement en votre faveur de ..."

    @ManyToOne(optional = false)
    private BankAccount bankAccount;
}