package com.example.ebank_application.service;

import com.example.ebank_application.dao.*;
import com.example.ebank_application.dto.TransactionDTO;
import com.example.ebank_application.dto.TransferRequest;
import com.example.ebank_application.enums.*;
import com.example.ebank_application.exception.BusinessException;
import com.example.ebank_application.security.SecurityUtils;
import com.example.ebank_application.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final CostumerRepository costumerRepository;
    @Override
    public List<TransactionDTO> getByAccountRib(String rib) {

        BankAccount account = bankAccountRepository.findByRib(rib)
                .orElseThrow(() -> new BusinessException("Compte introuvable"));

        return transactionRepository.findByBankAccount(account)
                .stream()
                .map(t -> TransactionDTO.builder()
                        .id(t.getId())
                        .type(t.getType())
                        .amount(t.getAmount())
                        .date(t.getDate())
                        .label(t.getLabel())
                        .build())
                .toList();
    }
    @Override
    public List<TransactionDTO> getMyTransactions() {

        String email = SecurityUtils.getCurrentUserEmail();

        Costumer costumer = costumerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        List<BankAccount> accounts =
                bankAccountRepository.findByCostumer(costumer);

        return transactionRepository.findByBankAccountIn(accounts)
                .stream()
                .map(t -> TransactionDTO.builder()
                        .id(t.getId())
                        .type(t.getType())
                        .amount(t.getAmount())
                        .date(t.getDate())
                        .label(t.getLabel())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void transfer(TransferRequest req) {

        // RG_11 : comptes existent
        BankAccount source = bankAccountRepository.findByRib(req.getSourceRib())
                .orElseThrow(() -> new BusinessException("RG_11: Compte source inexistant"));

        BankAccount destination = bankAccountRepository.findByRib(req.getDestinationRib())
                .orElseThrow(() -> new BusinessException("RG_11: Compte destination inexistant"));

        // RG_12 : compte source ouvert
        if (source.getStatus() != AccountStatus.OPENED) {
            throw new BusinessException("RG_12: Compte source non actif");
        }

        // RG_13 : solde suffisant
        if (source.getBalance() < req.getAmount()) {
            throw new BusinessException("RG_13: Solde insuffisant");
        }

        // Débit
        source.setBalance(source.getBalance() - req.getAmount());
        Transaction debit = Transaction.builder()
                .type(TransactionType.DEBIT)
                .amount(req.getAmount())
                .date(LocalDateTime.now())
                .label(req.getLabel())
                .bankAccount(source)
                .build();

        // Crédit
        destination.setBalance(destination.getBalance() + req.getAmount());
        Transaction credit = Transaction.builder()
                .type(TransactionType.CREDIT)
                .amount(req.getAmount())
                .date(LocalDateTime.now())
                .label(req.getLabel())
                .bankAccount(destination)
                .build();

        // Sauvegarde atomique
        bankAccountRepository.save(source);
        bankAccountRepository.save(destination);

        transactionRepository.save(debit);
        transactionRepository.save(credit);
    }
}
