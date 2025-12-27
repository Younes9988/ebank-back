package com.example.ebank_application.service;

import com.example.ebank_application.dto.TransactionDTO;
import com.example.ebank_application.dto.TransferRequest;

import java.util.List;

public interface ITransactionService {
    void transfer(TransferRequest request);
    List<TransactionDTO> getByAccountRib(String rib);
    List<TransactionDTO> getMyTransactions();
}