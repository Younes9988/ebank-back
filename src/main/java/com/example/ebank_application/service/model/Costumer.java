package com.example.ebank_application.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id")
public class Costumer extends User {
    private String identityNumber;
    private String dateOfBirth;
    private String address;
}
