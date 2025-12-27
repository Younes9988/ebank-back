package com.example.ebank_application.dao;

import com.example.ebank_application.service.ICostumerService;
import com.example.ebank_application.service.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> {
}
