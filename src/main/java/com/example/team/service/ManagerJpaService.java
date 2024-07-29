package com.example.team.service;

import com.example.team.model.Manager;
import com.example.team.repository.ManagerJpaRepository;
import com.example.team.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerJpaService implements ManagerRepository {

    @Autowired
    private ManagerJpaRepository managerJpaRepository;

    @Override
    public List<Manager> getManagers() {
        return managerJpaRepository.findAll();
    }
}
