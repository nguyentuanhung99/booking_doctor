package com.cg.inventorydepartmentservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.inventorydepartmentservice.dto.DepartmentRequest;
import com.cg.inventorydepartmentservice.dto.UpdateDepartmentRequest;
import com.cg.inventorydepartmentservice.exception.ResponseObject;
import com.cg.inventorydepartmentservice.service.DepartmentService;

import javax.validation.Valid;
@RestController
@RequestMapping({"/department"})
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> getDepartmentById(@PathVariable("id") Integer id) {
        return ResponseObject.success(departmentService.getDetailDepartment(id));
    }

    @GetMapping({"/all"})
    @ResponseBody
    ResponseEntity<?> getAllDepartment() {
        return ResponseObject.success(departmentService.getAllDepartment());
    }


    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertDepartment(@RequestPart("department") @Valid DepartmentRequest departmentRequest) {
        return ResponseObject.createSuccess(departmentService.createDepartment(departmentRequest));
    }

    @PutMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> updateDepartment(@RequestPart("department") @Valid UpdateDepartmentRequest updateDepartmentRequest) {
        return ResponseObject.createSuccess(departmentService.update(updateDepartmentRequest));
    }
    

    @DeleteMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> removeDepartment(@PathVariable("id") Integer id) {
    	departmentService.delete(id);
        return ResponseObject.success(id);
    }

}

