package com.example.team.controller;

import com.example.team.model.Manager;
import com.example.team.repository.ManagerRepository;
import com.example.team.service.ManagerJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerController {
    @Autowired
    private ManagerJpaService managerJpaService;

    @GetMapping("/managers")
    public List<Manager> getManagers(){
        return managerJpaService.getManagers();
    }
}
