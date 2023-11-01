package com.cg.inventoryappointmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.inventoryappointmentservice.entity.Appointment;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {


}
