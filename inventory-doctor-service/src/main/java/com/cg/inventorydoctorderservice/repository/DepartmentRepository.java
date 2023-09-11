package com.cg.inventorydoctorderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorydoctorderservice.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
