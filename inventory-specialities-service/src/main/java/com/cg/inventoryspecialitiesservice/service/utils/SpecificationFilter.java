package com.cg.inventoryspecialitiesservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventoryspecialitiesservice.entity.Specialities;

import javax.persistence.criteria.Predicate;

@Component
public class SpecificationFilter {



    public Specification<Specialities> create() {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();

            return p;
        };
    }

  
}

