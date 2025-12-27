package com.example.ebank_application.dao;

import com.example.ebank_application.service.model.BankAccount;
import com.example.ebank_application.service.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    Optional<BankAccount> findByRib(String rib);
    List<BankAccount> findByCostumer(Costumer costumer);

}
