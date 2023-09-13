package com.cg.inventorypatientservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.inventorypatientservice.dto.PatientRequest;
import com.cg.inventorypatientservice.dto.updatePatientRequest;
import com.cg.inventorypatientservice.exception.ResponseObject;
import com.cg.inventorypatientservice.service.PatientService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
@RestController
@RequestMapping({"/patient"})
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


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertPatient(@RequestPart("patient") @Valid PatientRequest patientRequest) {
        return ResponseObject.createSuccess(patientService.createPatient(patientRequest));
    }

    @PutMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
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

}

