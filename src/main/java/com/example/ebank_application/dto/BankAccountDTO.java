package com.example.ebank_application.dto;

import com.example.ebank_application.enums.AccountStatus;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountDTO {
    private Long id;
    private String rib;
    private Double balance;
    private AccountStatus status;
    private Long costumerId;
}