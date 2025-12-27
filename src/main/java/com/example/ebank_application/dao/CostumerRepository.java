package com.example.ebank_application.dao;

import com.example.ebank_application.service.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer,Long> {
    Optional<Costumer> findByIdentityNumber(String identityNumber);
    Optional<Costumer> findByEmail(String email);
}
