package com.cg.inventorydepartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorydepartmentservice.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
