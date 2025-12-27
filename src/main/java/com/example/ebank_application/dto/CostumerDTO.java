package com.example.ebank_application.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CostumerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String dateOfBirth;
    private String email;
    private String address;
}
