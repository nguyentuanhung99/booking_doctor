package com.cg.inventorydoctorderservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventorydoctorderservice.entity.Appointment;
import com.cg.inventorydoctorderservice.entity.Doctor;
import com.cg.inventorydoctorderservice.entity.Review;

import javax.persistence.criteria.Predicate;

@Component
public class SpecificationFilter {

	public Specification<Doctor> doctorSpecification(String name, String gender, Integer departmentId,
			Integer specialityId) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();

			if (!StringUtils.isEmpty(name)) {
	            p = cb.and(p, cb.like(cb.concat(root.get("lastName"), root.get("firstName")), "%" + name + "%"));
	        }

			if (!StringUtils.isEmpty(gender)) {
				p = cb.and(p, cb.equal(root.get("gender"), gender));
			}

			if (departmentId != null && departmentId != 0) {
				p = cb.and(p, cb.equal(root.get("departmentId"), departmentId));
			}

			if (specialityId != null && specialityId != 0) {
				p = cb.and(p, cb.equal(root.get("specialityId"), specialityId));
			}

			return p;
		};
	}

	public Specification<Review> reviewSpecification(Integer idDoctor) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			p = cb.and(p, cb.equal(root.get("idDoctor"), idDoctor));
			p = cb.and(p, cb.equal(root.get("checkDl"), true));
			return p;
		};
	}

	public Specification<Appointment> appointmentSpecification(Integer id) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			p = cb.and(p, cb.equal(root.get("idDoctor"), id));
			p = cb.and(p, cb.equal(root.get("Trangthai"), true));
			return p;
		};
	}

	public Specification<Appointment> appointmentOfPatientSpecification(Integer id) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			p = cb.and(p, cb.equal(root.get("idPatient"), id));
			p = cb.and(p, cb.equal(root.get("Trangthai"), true));
			return p;
		};
	}

	public Specification<Appointment> appointmentOfPatientDictintSpecification(Integer id) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			cq.groupBy(root.get("idPatient"));
			p = cb.and(p, cb.equal(root.get("idDoctor"), id));
			p = cb.and(p, cb.equal(root.get("Trangthai"), true));

			return p;
		};
	}

}
