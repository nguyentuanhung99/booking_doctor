package com.cg.consumerservice.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.cg.consumerservice.dto.CustomerListDto;
import com.cg.consumerservice.dto.ConsumerDto;
import com.cg.consumerservice.entity.Consumer;
import com.cg.consumerservice.payload.ApiResponse;

public interface ConsumerService {
  Consumer addConsumer(ConsumerDto consumer);

  Consumer getConsumerById(Long id);

  ResponseEntity<ApiResponse> deleteById(Long id);

  Page<CustomerListDto> getAllConsumers(Integer pageNo, Integer pageSize, String sortBy);
}
