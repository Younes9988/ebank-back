package com.example.ebank_application.service;

import com.example.ebank_application.dto.*;

import java.util.List;

public interface IBankAccountService {
    BankAccountDTO create(CreateBankAccountRequest request);
    List<BankAccountDTO> findAll();
    List<BankAccountDTO> findMyAccounts();
}