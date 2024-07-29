package com.example.team.repository;

import com.example.team.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerJpaRepository extends JpaRepository<Manager, UUID> {
}
