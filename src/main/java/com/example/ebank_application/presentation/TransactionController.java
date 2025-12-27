package com.example.ebank_application.presentation;

import com.example.ebank_application.dto.TransactionDTO;
import com.example.ebank_application.dto.TransferRequest;
import com.example.ebank_application.service.ITransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;
    @GetMapping("/account/{rib}")
    public List<TransactionDTO> getByAccount(@PathVariable String rib) {
        return transactionService.getByAccountRib(rib);
    }
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @Valid @RequestBody TransferRequest request) {

        transactionService.transfer(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/me")
    public List<TransactionDTO> myTransactions() {
        return transactionService.getMyTransactions();
    }
}
