package com.example.ebank_application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransferRequest {

    @NotBlank
    private String sourceRib;

    @NotBlank
    private String destinationRib;

    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String label;
}
