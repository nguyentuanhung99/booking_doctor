package com.cg.inventoryreviewservice.controller;

import lombok.AllArgsConstructor;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.inventoryreviewservice.dto.LoginRequest;
import com.cg.inventoryreviewservice.dto.PatientRequest;
import com.cg.inventoryreviewservice.dto.updatePatientRequest;
import com.cg.inventoryreviewservice.entity.UpdatePassWordPriciple;
import com.cg.inventoryreviewservice.exception.ResponseObject;
import com.cg.inventoryreviewservice.service.PatientService;

import java.security.Principal;

import javax.validation.Valid;
import javax.validation.constraints.Min;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({"patient"})
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;


    @GetMapping("/filter")
    ResponseEntity<?> filter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        return ResponseObject.success(patientService.filter(name,gender, size, page, desc, orderBy));
    }

    @GetMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> getPatientById(@PathVariable("id") Integer id) {
        return ResponseObject.success(patientService.getDetailPatient(id));
    }

    @GetMapping({"/all"})
    @ResponseBody
    ResponseEntity<?> getAllpatient() {
        return ResponseObject.success(patientService.getAllPatient());
    }


    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertPatient(@RequestPart("patient") @Valid PatientRequest patientRequest) {
        return ResponseObject.createSuccess(patientService.createPatient(patientRequest));
    }

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> updatePatient(@RequestPart("patient") @Valid updatePatientRequest updatePatientRequest) {
        return ResponseObject.createSuccess(patientService.update(updatePatientRequest));
    }
    

    @DeleteMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> removePatient(@PathVariable("id") Integer id) {
    	patientService.delete(id);
        return ResponseObject.success(id);
    }

    @PostMapping(value = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> authenticateUser(@RequestPart("patient") @Valid LoginRequest loginRequest) {

    	return ResponseObject.createSuccess(patientService.signin(loginRequest.getUsername(),loginRequest.getPassword()));
    }
    
    @GetMapping({"/myPatient"})
    @ResponseBody
    ResponseEntity<?> getUserByPricipal(Principal principal) {
        return ResponseObject.success(patientService.getPrinciple(principal));
    }
    
    @PutMapping(value = "/change-password", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> updatePasswordByPricipal(Principal principal,@RequestPart("doctor") @Valid UpdatePassWordPriciple updatePassWordPriciple) {
        return ResponseObject.success(patientService.updatePassword(principal,updatePassWordPriciple));
    }

}

