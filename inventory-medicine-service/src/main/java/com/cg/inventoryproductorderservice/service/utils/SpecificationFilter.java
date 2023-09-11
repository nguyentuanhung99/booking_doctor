package com.cg.inventoryproductorderservice.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cg.inventoryproductorderservice.entity.Medicine;

import javax.persistence.criteria.Predicate;

@Component
public class SpecificationFilter {



    public Specification<Medicine> create(String nameMedicine) {
        return (root, cq, cb) -> {
            Predicate p = cb.conjunction();

            if (!StringUtils.isEmpty(nameMedicine)) {
                p = cb.and(p, cb.like(root.get("nameMedicine"), "%" + nameMedicine + "%"));
            }
            return p;
        };
    }

  
}

