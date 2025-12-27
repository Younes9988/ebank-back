package com.example.ebank_application.presentation;

import com.example.ebank_application.dto.*;
import com.example.ebank_application.service.IBankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final IBankAccountService bankAccountService;
    @GetMapping
    public List<BankAccountDTO> getAll() {
        return bankAccountService.findAll();
    }
    @GetMapping("/me")
    public List<BankAccountDTO> myAccounts() {
        return bankAccountService.findMyAccounts();
    }
    @PostMapping
    public ResponseEntity<BankAccountDTO> create(
            @Valid @RequestBody CreateBankAccountRequest request) {

        BankAccountDTO dto = bankAccountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
