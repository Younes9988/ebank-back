package com.example.ebank_application.service;

import com.example.ebank_application.dao.CostumerRepository;
import com.example.ebank_application.dto.*;
import com.example.ebank_application.enums.Role;
import com.example.ebank_application.exception.BusinessException;
import com.example.ebank_application.security.SecurityUtils;
import com.example.ebank_application.service.model.Costumer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CostumerServiceImpl implements ICostumerService {

    private final CostumerRepository costumerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public List<CostumerDTO> findAll() {
        return costumerRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public CostumerDTO findById(Long id) {
        Costumer c = costumerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Client introuvable"));
        return toDTO(c);
    }
    @Override
    public CostumerDTO getCurrentCustomer() {

        String email = SecurityUtils.getCurrentUserEmail();

        Costumer c = costumerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        return toDTO(c);
    }

    @Override
    public CostumerDTO create(CreateCostumerRequest req) {
        // RG_4 : identité unique
        if (costumerRepository.findByIdentityNumber(req.getIdentityNumber()).isPresent()) {
            throw new BusinessException("RG_4: Le numéro d’identité existe déjà");
        }

        // RG_6 : email unique
        if (costumerRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new BusinessException("RG_6: L’adresse mail existe déjà");
        }

        // Générer credentials (RG_7)
        String login = req.getEmail(); // simple: login = email
        String rawPassword = generatePassword(10);

        Costumer c = Costumer.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .identityNumber(req.getIdentityNumber())
                .dateOfBirth(req.getDateOfBirth())
                .address(req.getAddress())
                .email(req.getEmail())
                .role(Role.CUSTOMER)
                .password(passwordEncoder.encode(rawPassword)) // RG_1 (crypté)
                .build();

        Costumer saved = costumerRepository.save(c);

        // RG_7 : envoyer mail (ici stub log)
        mailService.sendCredentials(saved.getEmail(), login, rawPassword);

        return toDTO(saved);
    }
    @Override
    public void changePassword(ChangePasswordRequest request) {

        String email = SecurityUtils.getCurrentUserEmail();

        Costumer customer = costumerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        // Verify old password
        if (!passwordEncoder.matches(request.getCurrentPassword(), customer.getPassword())) {
            throw new BusinessException("Current password is incorrect");
        }

        //Encode & update new password
        customer.setPassword(passwordEncoder.encode(request.getNewPassword()));

        costumerRepository.save(customer);
    }

    private CostumerDTO toDTO(Costumer c) {
        return CostumerDTO.builder()
                .id(c.getId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .identityNumber(c.getIdentityNumber())
                .dateOfBirth(c.getDateOfBirth())
                .email(c.getEmail())
                .address(c.getAddress())
                .build();
    }

    private String generatePassword(int len) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789@#";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(chars.charAt(r.nextInt(chars.length())));
        return sb.toString();
    }
}
