package com.cg.inventorydoctorderservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cg.inventorydoctorderservice.entity.Specialities;


@Repository
public interface SpecialitiesRepository extends JpaRepository<Specialities, Integer>, JpaSpecificationExecutor<Specialities> {
}
