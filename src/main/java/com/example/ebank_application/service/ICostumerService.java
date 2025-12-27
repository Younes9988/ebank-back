package com.example.ebank_application.service;


import com.example.ebank_application.dto.ChangePasswordRequest;
import com.example.ebank_application.dto.CostumerDTO;
import com.example.ebank_application.dto.CreateCostumerRequest;
import com.example.ebank_application.service.model.Costumer;

import java.util.List;
import java.util.Optional;


public interface ICostumerService {
    List<CostumerDTO> findAll();
    CostumerDTO findById(Long id);
    CostumerDTO getCurrentCustomer();
    CostumerDTO create(CreateCostumerRequest request);
    void changePassword(ChangePasswordRequest request);
}
