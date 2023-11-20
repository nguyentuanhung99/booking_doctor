package com.cg.inventorypatientservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorypatientservice.entity.Favorite;
import com.cg.inventorypatientservice.entity.Review;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

	List<Favorite> findAll(Specification<Favorite> documentFavoriteSpecification);

    Page<Favorite> findAll(Specification<Favorite> documentFavoriteSpecification, Pageable pageable);

}
