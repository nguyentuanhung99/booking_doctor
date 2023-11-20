package com.cg.inventoryauthservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventoryauthservice.entity.Doctor;
import com.cg.inventoryauthservice.entity.Patient;
import com.cg.inventoryauthservice.entity.Review;
import com.cg.inventoryauthservice.entity.Specialities;

import javax.persistence.criteria.Predicate;

@Component
public class SpecificationFilter {

	public Specification<Doctor> doctorSpecification(String name, String gender) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();

			if (!StringUtils.isEmpty(name)) {
				p = cb.and(p, cb.like(root.get("lastName"), "%" + name + "%"));

			}

			if (!StringUtils.isEmpty(gender)) {
				p = cb.and(p, cb.equal(root.get("gender"), "%" + gender + "%"));
			}
			return p;
		};
	}

	public Specification<Specialities> create() {
		// TODO Auto-generated method stub
		return null;
	}

	public Specification<Review> reviewSpecification() {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            
            p = cb.and(p, cb.equal(root.get("checkDl"), true ));
//            p = cb.and(p, cb.isNotNull(root.get("listIdRely")));
//            
            return p;
        };
	}

	public Specification<Patient> patientSpecification() {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            return p;
        };
	}


}
