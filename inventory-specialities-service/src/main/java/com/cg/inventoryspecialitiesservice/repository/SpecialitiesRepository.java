package com.cg.inventoryspecialitiesservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventoryspecialitiesservice.entity.Specialities;

@Repository
public interface SpecialitiesRepository extends JpaRepository<Specialities, Integer> {
    Page<Specialities> findAll(Specification<Specialities> specialitiesSpecification, Pageable pageable);
}
