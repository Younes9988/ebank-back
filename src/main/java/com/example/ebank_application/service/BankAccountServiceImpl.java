package com.example.ebank_application.service;

import com.example.ebank_application.dao.*;
import com.example.ebank_application.dto.*;
import com.example.ebank_application.enums.AccountStatus;
import com.example.ebank_application.exception.BusinessException;
import com.example.ebank_application.security.SecurityUtils;
import com.example.ebank_application.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements IBankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CostumerRepository costumerRepository;
    @Override
    public List<BankAccountDTO> findAll() {
        return bankAccountRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
    @Override
    public List<BankAccountDTO> findMyAccounts() {

        String email = SecurityUtils.getCurrentUserEmail();

        Costumer costumer = costumerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        return bankAccountRepository.findByCostumer(costumer)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public BankAccountDTO create(CreateBankAccountRequest req) {

        // RG_8 : le client doit exister
        Costumer costumer = costumerRepository.findById(req.getCostumerId())
                .orElseThrow(() ->
                        new BusinessException("RG_8: Client inexistant"));

        // RG_9 : RIB unique
        if (bankAccountRepository.findByRib(req.getRib()).isPresent()) {
            throw new BusinessException("RG_9: RIB déjà existant");
        }

        BankAccount account = BankAccount.builder()
                .rib(req.getRib())
                .balance(req.getInitialBalance())
                .status(AccountStatus.OPENED) // RG_10
                .costumer(costumer)
                .build();

        BankAccount saved = bankAccountRepository.save(account);

        return toDTO(saved);
    }

    private BankAccountDTO toDTO(BankAccount acc) {
        return BankAccountDTO.builder()
                .id(acc.getId())
                .rib(acc.getRib())
                .balance(acc.getBalance())
                .status(acc.getStatus())
                .costumerId(acc.getCostumer().getId())
                .build();
    }
}
