package com.cg.inventorypatientservice.controller;

import lombok.AllArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventorypatientservice.dto.AppointmentRequest;
import com.cg.inventorypatientservice.dto.LoginRequest;
import com.cg.inventorypatientservice.dto.MessageRequest;
import com.cg.inventorypatientservice.dto.PatientRequest;
import com.cg.inventorypatientservice.dto.ReviewRequest;
import com.cg.inventorypatientservice.dto.UpdateAppointmentRequest;
import com.cg.inventorypatientservice.dto.UpdateFavorite;
import com.cg.inventorypatientservice.dto.updatePatientPaticipantRequest;
import com.cg.inventorypatientservice.dto.updatePatientRequest;
import com.cg.inventorypatientservice.entity.UpdatePassWordPriciple;
import com.cg.inventorypatientservice.exception.ResponseObject;
import com.cg.inventorypatientservice.service.PatientService;

import java.io.IOException;
import java.io.InputStream;
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
    
    @GetMapping("/view-myApointment")
    ResponseEntity<?> viewAppointment(
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            Principal principal
    ) {
        return ResponseObject.success(patientService.viewMyAppointment( principal, size, page, desc, orderBy));
    }
    
    
    @GetMapping("/filter-byId")
    ResponseEntity<?> viewAppointment(
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(name = "id") Integer id
    ) {
        return ResponseObject.success(patientService.findAppointmentById( id, size, page, desc, orderBy));
    }

    @GetMapping({"/getById/{id}"})
    @ResponseBody
    ResponseEntity<?> getPatientById(@PathVariable("id") Integer id) {
        return ResponseObject.success(patientService.getDetailPatient(id));
    }
    
    @GetMapping({"/get-review/{id}"})
    @ResponseBody
    ResponseEntity<?> getReviewById(@PathVariable("id") Integer id) {
        return ResponseObject.success(patientService.getDetailReview(id));
    }
    
    @GetMapping({"/get-appointment/{id}"})
    @ResponseBody
    ResponseEntity<?> getAppointmentById(@PathVariable("id") Integer id) {
        return ResponseObject.success(patientService.getDetailAppointment(id));
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

    @PostMapping(value = "/update-myself", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> updateUserByPricipal(Principal principal,@RequestPart("patient") @Valid updatePatientPaticipantRequest updatePatientRequest) throws IOException {
        return ResponseObject.success(patientService.updatePrinciple(principal,updatePatientRequest));
    }
    
    @PostMapping(value = "/view-picture", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> viewPicture(Principal principal , @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseObject.success(patientService.viewPicture(principal,file));
    }

    @PutMapping(value = "/update-appointment", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> updateAppointment(@RequestPart("appointment") @Valid UpdateAppointmentRequest updateAppointmentRequest) {
        return ResponseObject.createSuccess(patientService.updateAppointment(updateAppointmentRequest));
    }
    
    @DeleteMapping({"/delete-patient/{id}"})
    @ResponseBody
    ResponseEntity<?> removePatient(@PathVariable("id") Integer id) {
    	patientService.delete(id);
        return ResponseObject.success(id);
    }
    
    @DeleteMapping({"/delete-appointment/{id}"})
    @ResponseBody
    ResponseEntity<?> removeAppointment(@PathVariable("id") Integer id) {
    	patientService.deleteAppointment(id);
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
    
    @PostMapping({"/create-review"})
    @ResponseBody
    ResponseEntity<?> createReview(Principal principal, @RequestPart("review") @Valid ReviewRequest reviewRequest , @RequestPart("idDoctor") @Valid Integer idDoctor ) {
        return ResponseObject.success(patientService.createReview(principal,reviewRequest,idDoctor));
    }
    
    @DeleteMapping({"/delete-review/{id}"})
    @ResponseBody
    ResponseEntity<?> removeReview(@PathVariable("id") Integer id) {
    	patientService.deleteReview(id);
        return ResponseObject.success(id);
    }
    

    
    @GetMapping("/filter-review")
    ResponseEntity<?> filterReview(
    		
            @RequestParam(name = "idPatient", required = false) Integer idPatient,
            @RequestParam(name = "idDoctor", required = false) Integer idDoctor,
            @RequestParam(name = "idReview", required = false) Integer idReview,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        return ResponseObject.success(patientService.filterReview(idPatient,idDoctor,idReview, size, page, desc, orderBy));
    }
    
    @PostMapping({"/create-appointment"})
    @ResponseBody
    ResponseEntity<?> createAppointment(Principal principal, @RequestPart("appointment") @Valid AppointmentRequest appointmentRequest , @RequestPart("idDoctor") @Valid Integer idDoctor ) {
        return ResponseObject.success(patientService.createAppointment(principal,appointmentRequest,idDoctor));
    }
    
    @PutMapping(value = "/change-password", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> updatePasswordByPricipal(Principal principal,@RequestPart("doctor") @Valid UpdatePassWordPriciple updatePassWordPriciple) {
        return ResponseObject.success(patientService.updatePassword(principal,updatePassWordPriciple));
    }

    
    @PostMapping(value = "/update-favorite", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    ResponseEntity<?> updateFavoriteByPricipal(Principal principal, @RequestPart("favorite") @Valid UpdateFavorite updateFavorite) {
        return ResponseObject.success(patientService.updateFavorite(principal,updateFavorite));
    }
    
    
    @GetMapping("/filter-favorite")
    ResponseEntity<?> filterReview(
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            Principal principal
    ) {
        return ResponseObject.success(patientService.getMyFavorite(principal, size, page, desc, orderBy));
    }
    
    
    @GetMapping("/check-favorite")
    ResponseEntity<?> filterReview(
            Principal principal,
            Integer idDoctor
    ) {
        return ResponseObject.success(patientService.checkDoctorFavorite(principal, idDoctor));
    }
    
    
    @GetMapping({"/getDetailAppointment/{id}"})
    @ResponseBody
    ResponseEntity<?> getDetailAppointmentById(@PathVariable("id") Integer id) {
        return ResponseObject.success(patientService.getDetailAppointment(id));
    }

    
    // messs
    @GetMapping("/filter-mess")
    ResponseEntity<?> filterMess(
    		
            @RequestParam(name = "idSent", required = false) Integer idSent,
            @RequestParam(name = "idReceive", required = false) Integer idReceive,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        return ResponseObject.success(patientService.findMessById(idSent,idReceive, size, page, desc, orderBy));
    }
    
    
    @PostMapping({"/create-mess"})
    @ResponseBody
    ResponseEntity<?> createMess(Principal principal, @RequestPart("message") @Valid MessageRequest messageRequest ) {
        return ResponseObject.success(patientService.createMessage(messageRequest));
    }
    // mess

}

