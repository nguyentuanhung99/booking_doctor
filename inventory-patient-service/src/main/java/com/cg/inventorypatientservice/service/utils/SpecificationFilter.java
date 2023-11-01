package com.cg.inventorypatientservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventorypatientservice.entity.Appointment;
import com.cg.inventorypatientservice.entity.Patient;
import com.cg.inventorypatientservice.entity.Review;

import javax.persistence.criteria.Predicate;

@Component
public class SpecificationFilter {

	public Specification<Patient> patientSpecification(String name, String gender) {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            
            if (!StringUtils.isEmpty(name)) {
                p = cb.and(p, cb.like(root.get("firstName") , "%" + name + "%"));

            }
            
            if (!StringUtils.isEmpty(gender)) {
                p = cb.and(p, cb.equal(root.get("gender"), "%" + gender + "%"));
            }
            return p;
        };
	}

	public Specification<Appointment> appointmentSpecification(Integer id) {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();
                p = cb.and(p, cb.equal(root.get("idPatient") , id ));
                p = cb.and(p, cb.equal(root.get("Trangthai") , true ));
            return p;
        };
	}

	public Specification<Review> reviewSpecification(Integer idPatient, Integer idDoctor, Integer idReview) {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();

            if (idPatient != null) {
                p = cb.and(p, cb.equal(root.get("idPatient") ,idPatient));

            }
            
            if (idDoctor!= null) {
                p = cb.and(p, cb.equal(root.get("idDoctor") ,idDoctor ));

            }
            
            if (idReview!= null) {
                p = cb.and(p, cb.equal(root.get("id"), idReview ));
            }
            return p;
        };
	}


  
}

