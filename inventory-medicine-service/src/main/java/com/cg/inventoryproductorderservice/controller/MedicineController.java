package com.cg.inventoryproductorderservice.controller;

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

import com.cg.inventoryproductorderservice.dto.MedicineRequest;
import com.cg.inventoryproductorderservice.dto.updateMedicineRequest;
import com.cg.inventoryproductorderservice.exception.ResponseObject;
import com.cg.inventoryproductorderservice.service.MedicineService;

@RestController
@RequestMapping("/medicine")
@CrossOrigin(origins = "*")
public class MedicineController {

	@Autowired
	MedicineService medicineService ;

    @GetMapping("/filter")
    ResponseEntity<?> filter(
            @RequestParam(name = "nameMedicine", required = false) String nameMedicine,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "k tim thay trang") int page,
            @RequestParam(name = "desc", defaultValue = "true") boolean desc,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        return ResponseObject.success(medicineService.filter(nameMedicine, size, page, desc, orderBy));
    }

    @GetMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> getMedicineById(@PathVariable("id") Integer id) {
        return ResponseObject.success(medicineService.getDetailMedicine(id));
    }

    @GetMapping({"/all"})
    @ResponseBody
    ResponseEntity<?> getAllMedicine() {
        return ResponseObject.success(medicineService.getAllMedicine());
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> insertMedicine(@RequestPart("medicine") @Valid MedicineRequest medicineRequest) {
        return ResponseObject.createSuccess(medicineService.createMedicine(medicineRequest));
    }

    @PutMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> updateMedicine(@RequestPart("medicine") @Valid updateMedicineRequest updateMedicineRequest) {
        return ResponseObject.createSuccess(medicineService.update(updateMedicineRequest));
    }

    @DeleteMapping({"/{id}"})
    @ResponseBody
    ResponseEntity<?> removeMedicine(@PathVariable("id") Integer id) {
    	medicineService.delete(id);
        return ResponseObject.success(id);
    }
}
