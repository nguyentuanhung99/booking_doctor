package com.cg.inventoryauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cg.inventoryauthservice.entity.Admin;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Admin, String> {
        
    boolean existsByUsername(String username);

    Admin findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
