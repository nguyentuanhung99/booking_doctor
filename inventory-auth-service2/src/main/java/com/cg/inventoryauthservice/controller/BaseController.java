package com.cg.inventoryauthservice.controller;

import lombok.AllArgsConstructor;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client.ClientRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventoryauthservice.dto.request.DoctorRequest;
import com.cg.inventoryauthservice.dto.request.LoginRequest;
import com.cg.inventoryauthservice.dto.request.PatientRequest;
import com.cg.inventoryauthservice.dto.request.RegisterRequest;
import com.cg.inventoryauthservice.dto.request.SpecialitiesRequest;
import com.cg.inventoryauthservice.dto.request.UpdatePassWordPriciple;
import com.cg.inventoryauthservice.dto.request.updateDoctorRequest;
import com.cg.inventoryauthservice.dto.request.updateSpecialitiesRequest;
import com.cg.inventoryauthservice.exception.ResponseObject;
import com.cg.inventoryauthservice.service.UserService;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@CrossOrigin
@RequestMapping({ "/admin" })
@AllArgsConstructor
public class BaseController {

	private final UserService userService;

	@CrossOrigin
	@PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	ResponseEntity<?> insertPatient(@RequestPart("admin") @Valid RegisterRequest registerRequest) {
		return ResponseObject.createSuccess(userService.signup(registerRequest));
	}

	@CrossOrigin
	@PostMapping(value = "/signin", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> authenticateUser(@RequestPart("admin") @Valid LoginRequest loginRequest) {

		return ResponseObject.createSuccess(userService.signin(loginRequest.getUsername(), loginRequest.getPassword()));
	}

	@CrossOrigin
	@GetMapping({ "/myUser" })
	@ResponseBody
	ResponseEntity<?> getUserByPricipal(Principal principal) {
		return ResponseObject.success(userService.getPrinciple(principal));
	}

	// Doctor-Admin

	@CrossOrigin
	@GetMapping({ "getDetailDoctor/{id}" })
	@ResponseBody
	ResponseEntity<?> getDoctorById(@PathVariable("id") Integer id) {
		return ResponseObject.success(userService.getDetailDoctor(id));
	}

	@CrossOrigin
	@GetMapping("/filter-doctor")
	ResponseEntity<?> filter(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "gender", required = false) String gender,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
			@RequestParam(name = "desc", defaultValue = "true") boolean desc,
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy) {
		return ResponseObject.success(userService.filterDoctor(name, gender, size, page, desc, orderBy));
	}

	@CrossOrigin
	@PostMapping(value = "/register-doctor", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	ResponseEntity<?> insertDoctor(@RequestPart("doctor") @Valid DoctorRequest doctorRequest) {
		return ResponseObject.createSuccess(userService.createDoctor(doctorRequest));
	}

	@CrossOrigin
	@PutMapping(value = "/update-doctor", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	ResponseEntity<?> updateDoctor(@RequestPart("doctor") @Valid updateDoctorRequest updateDoctorRequest) {
		return ResponseObject.createSuccess(userService.updateDoctor(updateDoctorRequest));
	}

	@CrossOrigin
	@PutMapping(value = "/change-password", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	ResponseEntity<?> updatePasswordByPricipal(
			@RequestPart("doctor") @Valid UpdatePassWordPriciple updatePassWordPriciple) {
		return ResponseObject.success(userService.updatePassword(updatePassWordPriciple));
	}

	@CrossOrigin
	@DeleteMapping({ "/delete-doctor/{id}" })
	@ResponseBody
	ResponseEntity<?> removeDoctor(@PathVariable("id") Integer id) {
		userService.deleteDoctor(id);
		return ResponseObject.success(id);
	}
	// Doctor-Admin

//    // Specialiti-Admin

	@CrossOrigin
	@GetMapping("/filter-specialities")
	ResponseEntity<?> filter(@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
			@RequestParam(name = "desc", defaultValue = "true") boolean desc,
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy) {
		return ResponseObject.success(userService.filterSpecialities(size, page, desc, orderBy));
	}

	@CrossOrigin
	@GetMapping({ "/specialities/{id}" })
	@ResponseBody
	ResponseEntity<?> getSpecialitiesById(@PathVariable("id") Integer id) {
		return ResponseObject.success(userService.getDetailSpecialities(id));
	}

	@CrossOrigin
	@PostMapping(value = "/insert-specialities", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	ResponseEntity<?> insertSpecialities(@RequestPart("Specialities") @Valid SpecialitiesRequest specialitiesRequest,
			@RequestPart("file") MultipartFile file) {
		return ResponseObject.createSuccess(userService.createSpecialities(specialitiesRequest, file));
	}

	@CrossOrigin
	@PutMapping(value = "/update-specialities", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	ResponseEntity<?> updateSpecialities(
			@RequestPart("Specialities") @Valid updateSpecialitiesRequest updateSpecialitiesRequest,
			@RequestPart("file") MultipartFile file) throws IOException {
		return ResponseObject.createSuccess(userService.updateSpecialities(updateSpecialitiesRequest, file));
	}

	@CrossOrigin
	@DeleteMapping({ "/delete-specialities/{id}" })
	@ResponseBody
	ResponseEntity<?> removeSpecialities(@PathVariable("id") Integer id) {
		userService.deleteSpecialities(id);
		return ResponseObject.success(id);
	}

//    // Specialiti-Admin

	// Review -admin
	@CrossOrigin
	@DeleteMapping({ "/delete-review/{id}" })
	@ResponseBody
	ResponseEntity<?> removeReview(@PathVariable("id") Integer id) {
		userService.deleteReview(id);
		return ResponseObject.success(id);
	}

	@CrossOrigin
	@GetMapping("/filter-review")
	ResponseEntity<?> filterReview(@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
			@RequestParam(name = "desc", defaultValue = "true") boolean desc,
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy) {
		return ResponseObject.success(userService.filterReview(size, page, desc, orderBy));
	}

	@CrossOrigin
	@GetMapping({ "/get-review/{id}" })
	@ResponseBody
	ResponseEntity<?> getReviewById(@PathVariable("id") Integer id) {
		return ResponseObject.success(userService.getDetailReview(id));
	}

	// Review -admin

	// Patient Admin

	@CrossOrigin
	@GetMapping("/filter-patient")
	ResponseEntity<?> filterPatient(
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
			@RequestParam(name = "desc", defaultValue = "true") boolean desc,
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy) {
		return ResponseObject.success(userService.filterPatient( size, page, desc, orderBy));
	}

	@CrossOrigin
	@GetMapping({ "/patient/{id}" })
	@ResponseBody
	ResponseEntity<?> getPatientById(@PathVariable("id") Integer id) {
		return ResponseObject.success(userService.getDetailPatient(id));
	}

//	@PostMapping(value = "/register-patient", consumes = { MediaType.APPLICATION_JSON_VALUE,
//			MediaType.MULTIPART_FORM_DATA_VALUE })
//	ResponseEntity<?> insertPatient(@RequestPart("patient") @Valid PatientRequest patientRequest) {
//		return ResponseObject.createSuccess(userService.createPatient(patientRequest));
//	}
//
//	@PutMapping(value = "/update-patient", consumes = { MediaType.APPLICATION_JSON_VALUE,
//			MediaType.MULTIPART_FORM_DATA_VALUE })
//	ResponseEntity<?> updatePatient(@RequestPart("patient") @Valid ClientRequest updatePatientRequest) {
//		return ResponseObject.createSuccess(userService.updatePatient(updatePatientRequest));
//	}

	// Patient Admin
	
	
	
}
