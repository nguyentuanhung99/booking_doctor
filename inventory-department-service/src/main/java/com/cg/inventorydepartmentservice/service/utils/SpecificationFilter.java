package com.cg.inventorydepartmentservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventorydepartmentservice.entity.Department;

import javax.persistence.criteria.Predicate;

@Component
public class SpecificationFilter {

	public Specification<Department> departmentSpecification(String name) {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();

            if (!StringUtils.isEmpty(name)) {
                p = cb.and(p, cb.like(root.get("name") , "%" + name + "%"));

            }
            return p;
        };
	}


  
}

