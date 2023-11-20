package com.cg.inventorypatientservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventorypatientservice.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	Page<Message> findAll(Specification<Message> documentMessageSpecification, Pageable pageable);

}
