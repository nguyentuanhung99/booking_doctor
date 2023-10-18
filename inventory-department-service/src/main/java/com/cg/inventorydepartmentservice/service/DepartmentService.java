package com.cg.inventorydepartmentservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.cg.inventorydepartmentservice.dto.DepartmentRequest;
import com.cg.inventorydepartmentservice.dto.UpdateDepartmentRequest;
import com.cg.inventorydepartmentservice.entity.Department;
import com.cg.inventorydepartmentservice.repository.DepartmentRepository;
import com.cg.inventorydepartmentservice.service.utils.SpecificationFilter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
@Service
@AllArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    private final SpecificationFilter specificationFilter;

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department createDepartment(DepartmentRequest departmentRequest) {
        Department departments = new Department();
        departments.setName(departmentRequest.getName());
        departments.setAddress(departmentRequest.getAddress());
        departments.setCreated_at(asDate(LocalDate.now()));
        departmentRepository.save(departments);
        return departments;
    }

    public Department getDetailDepartment(Integer id) {
        return departmentRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }

    @Transactional
    public Department update(UpdateDepartmentRequest updateDepartmentRequest) {
        Department departmentUpdate = departmentRepository.findById(updateDepartmentRequest.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        departmentUpdate.setName(updateDepartmentRequest.getName());
        departmentUpdate.setAddress(updateDepartmentRequest.getAddress());
        departmentUpdate.setUpdate_at(asDate(LocalDate.now()));
        departmentRepository.save(departmentUpdate);
        return departmentUpdate;
    }
    
    
    public void delete(Integer id) {
        if (!departmentRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
        }
        departmentRepository.deleteById(id);
    }
    
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      }
}
