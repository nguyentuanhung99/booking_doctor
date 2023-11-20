package com.cg.inventoryauthservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventoryauthservice.entity.Review;
;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	Page<Review> findAll(Specification<Review> documentPatientSpecification, Pageable pageable);

}
