package com.cg.inventorydoctorderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.inventorydoctorderservice.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {


	@Query(value="select * from Education e where e.id_doctor= :idDoctor", nativeQuery=true)
	List<Education> findAllByIdDoctor(Integer idDoctor);

}
