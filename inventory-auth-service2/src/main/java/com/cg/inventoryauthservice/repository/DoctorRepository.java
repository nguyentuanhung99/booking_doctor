package com.cg.inventoryauthservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cg.inventoryauthservice.entity.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {

    Page<Doctor> findAll(Specification<Doctor> doctorSpecification, Pageable pageable);

	Doctor findByUsername(String username);
	
	boolean existsByUsername(String username);
}
