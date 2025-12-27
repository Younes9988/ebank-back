package com.example.ebank_application.presentation;

import com.example.ebank_application.dto.*;
import com.example.ebank_application.service.ICostumerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CostumerController {

    private final ICostumerService costumerService;
    @GetMapping
    public List<CostumerDTO> getAll() {
        return costumerService.findAll();
    }
    @GetMapping("/me")
    public CostumerDTO me() {
        return costumerService.getCurrentCustomer();
    }
    @PostMapping
    public ResponseEntity<CostumerDTO> create(@Valid @RequestBody CreateCostumerRequest request) {
        CostumerDTO created = costumerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        costumerService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
