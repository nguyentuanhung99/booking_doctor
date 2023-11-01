package com.cg.inventoryspecialitiesservice.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventoryspecialitiesservice.dto.SpecialitiesRequest;
import com.cg.inventoryspecialitiesservice.dto.updateSpecialitiesRequest;
import com.cg.inventoryspecialitiesservice.exception.ResponseObject;
import com.cg.inventoryspecialitiesservice.service.SpecialitiesService;



@RestController
@RequestMapping("/specialities")
@CrossOrigin(origins = "*")
public class SpecialitiesController {
	
	@Autowired
	SpecialitiesService specialitiesService ;

    @GetMapping("/filter")
    ResponseEntity<?> filter(
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        return ResponseObject.success(specialitiesService.filter( size, page, desc, orderBy));
    }

    @GetMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> getSpecialitiesById(@PathVariable("id") Integer id) {
        return ResponseObject.success(specialitiesService.getDetailSpecialities(id));
    }

    @GetMapping({"/all"})
    @ResponseBody
    ResponseEntity<?> getAllSpecialities() {
        return ResponseObject.success(specialitiesService.getAllSpecialities());
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertSpecialities(@RequestPart("Specialities") @Valid SpecialitiesRequest specialitiesRequest,@RequestPart("file") MultipartFile file) {
        return ResponseObject.createSuccess(specialitiesService.createSpecialities(specialitiesRequest,file));
    }

    @PutMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> updateSpecialities(@RequestPart("Specialities") @Valid updateSpecialitiesRequest updateSpecialitiesRequest , @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseObject.createSuccess(specialitiesService.update(updateSpecialitiesRequest,file));
    }

    @DeleteMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> removeSpecialities(@PathVariable("id") Integer id) {
    	specialitiesService.delete(id);
        return ResponseObject.success(id);
    }
    
}
