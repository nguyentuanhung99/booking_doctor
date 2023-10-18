package com.cg.inventorydepartmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorydepartmentservice.entity.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Page<Admin> findAll(Specification<Admin> patientSpecification, Pageable pageable);

    Admin findByUsername(String username);

	boolean existsByUsername(String userName);

}
