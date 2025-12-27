package com.example.ebank_application.dao;

import com.example.ebank_application.service.model.BankAccount;
import com.example.ebank_application.service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByBankAccount(BankAccount bankAccount);
    List<Transaction> findByBankAccountIn(List<BankAccount> accounts);
}
