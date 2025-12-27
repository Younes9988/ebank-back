package com.example.ebank_application.dto;

import com.example.ebank_application.enums.TransactionType;
import com.example.ebank_application.service.model.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data @Builder
public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private LocalDateTime date;
    private String label;
}