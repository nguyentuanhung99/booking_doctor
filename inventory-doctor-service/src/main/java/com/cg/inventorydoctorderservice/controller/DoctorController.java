package com.cg.inventorydoctorderservice.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventorydoctorderservice.dto.DoctorRequest;
import com.cg.inventorydoctorderservice.dto.LoginRequest;
import com.cg.inventorydoctorderservice.dto.UpdateDoctorRequestPriciple;
import com.cg.inventorydoctorderservice.dto.UpdatePassWordPriciple;
import com.cg.inventorydoctorderservice.dto.updateDoctorRequest;
import com.cg.inventorydoctorderservice.exception.ResponseObject;
import com.cg.inventorydoctorderservice.service.DoctorService;

import java.security.Principal;

import javax.validation.Valid;
import javax.validation.constraints.Min;
@RestController
@RequestMapping({"/doctor"})
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorController {
    private final DoctorService doctorService;


    @GetMapping("/filter")
    ResponseEntity<?> filter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        return ResponseObject.success(doctorService.filter(name,gender, size, page, desc, orderBy));
    }

    @GetMapping({"getDetailDoctor/{id}"})
    @ResponseBody
    ResponseEntity<?> getDoctorById(@PathVariable("id") Integer id) {
        return ResponseObject.success(doctorService.getDetailDoctor(id));
    }
    
    @GetMapping({"getDetailEducation/{id}"})
    @ResponseBody
    ResponseEntity<?> getEducationById(@PathVariable("id") Integer id) {
        return ResponseObject.success(doctorService.getDetailEducation(id));
    }
    
    @GetMapping({"getDetailExperience/{id}"})
    @ResponseBody
    ResponseEntity<?> getExperienceById(@PathVariable("id") Integer id) {
        return ResponseObject.success(doctorService.getDetailExperience(id));
    }
    
    @GetMapping({"getDetailAwards/{id}"})
    @ResponseBody
    ResponseEntity<?> getAwardsById(@PathVariable("id") Integer id) {
        return ResponseObject.success(doctorService.getDetailAwards(id));
    }

    @GetMapping({"/all"})
    @ResponseBody
    ResponseEntity<?> getAllDoctor() {
        return ResponseObject.success(doctorService.getAllDoctor());
    }


    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertDoctor(@RequestPart("doctor") @Valid DoctorRequest doctorRequest) {
        return ResponseObject.createSuccess(doctorService.createDoctor(doctorRequest));
    }

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> updateDoctor(@RequestPart("doctor") @Valid updateDoctorRequest updateDoctorRequest) {
        return ResponseObject.createSuccess(doctorService.update(updateDoctorRequest));
    }
    
//    @PutMapping(value = "/update_Booking_time", consumes = {MediaType.APPLICATION_JSON_VALUE,
//            MediaType.MULTIPART_FORM_DATA_VALUE})
//    ResponseEntity<?> updateBookingTime(@RequestPart("doctor") @Valid com.cg.inventorydoctorderservice.dto.updateBookingTime updateBookingTime) {
//        return ResponseObject.createSuccess(doctorService.updateBookingTime(updateBookingTime));
//    }

    @DeleteMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> removeDoctor(@PathVariable("id") Integer id) {
    	doctorService.delete(id);
        return ResponseObject.success(id);
    }

    @PostMapping(value = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> authenticateUser(@RequestPart("doctor") @Valid LoginRequest loginRequest) {

    	return ResponseObject.createSuccess(doctorService.signin(loginRequest.getUsername(),loginRequest.getPassword()));
    }
    
    @GetMapping({"/myDoctor"})
    @ResponseBody
    ResponseEntity<?> getUserByPricipal(Principal principal) {
        return ResponseObject.success(doctorService.getPrinciple(principal));
    }
    
    @PostMapping(value = "/update-myself", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> updateUserByPricipal(Principal principal,@RequestPart("doctor") @Valid UpdateDoctorRequestPriciple updateDoctorRequestPriciple,  @RequestPart("file") MultipartFile file) {
        return ResponseObject.success(doctorService.updatePrinciple(principal,updateDoctorRequestPriciple,file));
    }
    
    @PutMapping(value = "/change-password", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> updatePasswordByPricipal(Principal principal,@RequestPart("doctor") @Valid UpdatePassWordPriciple updatePassWordPriciple) {
        return ResponseObject.success(doctorService.updatePassword(principal,updatePassWordPriciple));
    }

}

