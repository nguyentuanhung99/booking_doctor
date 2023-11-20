package com.cg.inventorydepartmentservice.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorydepartmentservice.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	List<Department> findAll(Specification<Department> documentDepartmentSpecification);


}
