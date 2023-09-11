package com.cg.consumerservice.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.consumerservice.entity.Consumer;
import com.cg.consumerservice.repository.ConsumerRepository;
import com.cg.consumerservice.util.ExcelGenerator;


@RestController
@RequestMapping("/api/customers")
public class ExcelExportRestAPI {
	@Autowired
    ConsumerRepository consumerRepository;
	 @GetMapping(value = "/download/customers.xlsx")
	    public ResponseEntity excelCustomersReport() throws IOException {
	        List<Consumer> customers = consumerRepository.findAll();
			
			ByteArrayInputStream in = ExcelGenerator.consumersToExcel(customers);
			// return IOUtils.toByteArray(in);
			
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=consumers.xlsx");
			
			 return ResponseEntity
		                .ok()
		                .headers(headers)
		                .body(new InputStreamResource(in));
	    }
}
