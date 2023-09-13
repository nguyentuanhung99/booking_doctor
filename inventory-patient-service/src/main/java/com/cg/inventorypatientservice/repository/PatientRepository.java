package com.cg.inventorypatientservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorypatientservice.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Page<Patient> findAll(Specification<Patient> patientSpecification, Pageable pageable);
}
