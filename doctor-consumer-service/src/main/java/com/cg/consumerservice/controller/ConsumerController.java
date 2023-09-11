package com.cg.consumerservice.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.consumerservice.dto.CustomerListDto;
import com.cg.consumerservice.dto.ConsumerDto;
import com.cg.consumerservice.entity.Consumer;
import com.cg.consumerservice.payload.ApiResponse;
import com.cg.consumerservice.service.ConsumerService;
import com.cg.consumerservice.util.AppConstants;
import com.cg.consumerservice.util.ExcelGenerator;

@RestController
@RequestMapping("/consumer")
@CrossOrigin(origins = "*")
public class ConsumerController {

  @Autowired
  private ConsumerService service;

  @PostMapping
  public ResponseEntity<Consumer> addConsumer(@Valid @RequestBody ConsumerDto consumer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.addConsumer(consumer));
  }

  @GetMapping
  public ResponseEntity<Page<CustomerListDto>> getAllConsumer(
      @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNo,
      @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
      @RequestParam(defaultValue = "leadId") String sortBy){
  Page<CustomerListDto> get = service.getAllConsumers(pageNo, pageSize, sortBy);
    return ResponseEntity.status(HttpStatus.OK).body(get);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Consumer> fetchConsumerById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getConsumerById(id));
  }

  @PutMapping
  public ResponseEntity<Consumer> updateConsumer(@Valid @RequestBody ConsumerDto consumer) {
    return ResponseEntity.status(HttpStatus.OK).body(service.addConsumer(consumer));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
	return service.deleteById(id);
  }
  
//  @GetMapping(value = "/download/consumers.xlsx")
//  public ResponseEntity excelCustomersReport() throws IOException {
//	  List<Consumer> listConsumers = service.getAllConsumers();
//		
//		ByteArrayInputStream in = ExcelGenerator.consumersToExcel(listConsumers);
//		// return IOUtils.toByteArray(in);
//		
//		HttpHeaders headers = new HttpHeaders();
//      headers.add("Content-Disposition", "attachment; filename=consumers.xlsx");
//		
//		 
//      return ResponseEntity
//              .ok()
//              .headers(headers)
//              .body(new InputStreamResource(in));
//  }

}
