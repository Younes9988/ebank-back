package com.example.ebank_application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateBankAccountRequest {

    @NotBlank
    private String rib;

    @NotNull
    private Double initialBalance;

    @NotNull
    private Long costumerId;
}