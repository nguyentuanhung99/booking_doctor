package com.cg.inventorydoctorderservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventorydoctorderservice.entity.Doctor;

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

  
}

