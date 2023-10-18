package com.cg.inventorydoctorderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.inventorydoctorderservice.entity.Education;
import com.cg.inventorydoctorderservice.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {


	@Query(value="select * from Experience e where e.id_doctor= :idDoctor", nativeQuery=true)
	List<Experience> findAllByIdDoctor(Integer idDoctor);

}
