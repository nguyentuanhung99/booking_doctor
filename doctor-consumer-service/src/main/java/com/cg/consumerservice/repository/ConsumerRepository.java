
package com.cg.consumerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.consumerservice.entity.Consumer;

@Repository
@Transactional
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
	
}
