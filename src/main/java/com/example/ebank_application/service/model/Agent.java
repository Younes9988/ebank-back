package com.example.ebank_application.service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name="id")
public class Agent extends User {

}
