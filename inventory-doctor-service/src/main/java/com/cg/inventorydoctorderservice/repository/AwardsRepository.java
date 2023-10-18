package com.cg.inventorydoctorderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.inventorydoctorderservice.entity.Awards;
import com.cg.inventorydoctorderservice.entity.Education;

@Repository
public interface AwardsRepository extends JpaRepository<Awards, Integer> {


	@Query(value="select * from Awards e where e.id_doctor= :idDoctor", nativeQuery=true)
	List<Awards> findAllByIdDoctor(Integer idDoctor);

}
