package com.example.ebank_application.service.model;

import com.example.ebank_application.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String firstName;
    protected String lastName;

    @Column(unique = true, nullable = false)
    protected String email;

    protected String password;

    @Enumerated(EnumType.STRING)
    protected Role role;
}
