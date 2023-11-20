package com.cg.inventorydoctorderservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.inventorydoctorderservice.entity.Appointment;




@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Page<Appointment> findAll(Specification<Appointment> documentAppointmentSpecification, Pageable pageable);

	List<Appointment> findAll(Specification<Appointment> documentAppointmentSpecification);


}
