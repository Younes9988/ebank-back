package com.example.ebank_application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateCostumerRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    private String identityNumber;

    @NotBlank
    private String dateOfBirth;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String address;
}