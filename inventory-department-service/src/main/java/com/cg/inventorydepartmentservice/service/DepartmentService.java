package com.cg.inventorydepartmentservice.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventorydepartmentservice.dto.DepartmentRequest;
import com.cg.inventorydepartmentservice.dto.UpdateDepartmentRequest;
import com.cg.inventorydepartmentservice.entity.Department;
import com.cg.inventorydepartmentservice.repository.DepartmentRepository;
import com.cg.inventorydepartmentservice.service.utils.ListResult;
import com.cg.inventorydepartmentservice.service.utils.SpecificationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    private final SpecificationFilter specificationFilter;
    
    @Value("${image.location}")
    private String location;
    
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department createDepartment(DepartmentRequest departmentRequest, MultipartFile file) {
        Department departments = new Department();
        departments.setName(departmentRequest.getName());
        departments.setAddress(departmentRequest.getAddress());
        
		String fileName = file.getOriginalFilename();
		try {
			FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		departments.setImage(fileName);
		
        departments.setCreated_at(new Date());
        departmentRepository.save(departments);
        return departments;
    }

    public Department getDetailDepartment(Integer id) {
        return departmentRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }

    @Transactional
    public Department update(UpdateDepartmentRequest updateDepartmentRequest,  MultipartFile file) {
        Department departmentUpdate = departmentRepository.findById(updateDepartmentRequest.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        departmentUpdate.setName(updateDepartmentRequest.getName());
        departmentUpdate.setAddress(updateDepartmentRequest.getAddress());
		String fileName = file.getOriginalFilename();
		try {
			FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		departmentUpdate.setImage(fileName);
        departmentUpdate.setUpdate_at(new Date());
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
    
    
	public List<Department> findDepartmentByName(String name ) {
		Specification<Department> documentDepartmentSpecification = specificationFilter
				.departmentSpecification(name);
		List<Department> myFavorite = departmentRepository.findAll(documentDepartmentSpecification);
 		return myFavorite;
	}
}
