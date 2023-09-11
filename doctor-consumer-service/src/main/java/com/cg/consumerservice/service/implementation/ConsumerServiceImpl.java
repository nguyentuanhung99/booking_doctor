
package com.cg.consumerservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.consumerservice.dto.CustomerListDto;
import com.cg.consumerservice.dto.ConsumerDto;
import com.cg.consumerservice.entity.Consumer;
import com.cg.consumerservice.exception.NotFoundException;
import com.cg.consumerservice.helper.ConsumerMapper;
import com.cg.consumerservice.payload.ApiResponse;
import com.cg.consumerservice.repository.ConsumerRepository;
import com.cg.consumerservice.service.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

  @Autowired
  private ConsumerRepository repository;

//  @Override
//  public Consumer addConsumer(ListConsumerDto consumer) {
//    return repository.save(ConsumerMapper.ConsumerEntityToDto(consumer));
//  }

  @Override
  public Page<CustomerListDto> getAllConsumers(Integer pageNo, Integer pageSize, String sortBy) {
	  Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
	  Page<CustomerListDto> pagedResult = repository.findAll(paging).map(p -> ConsumerMapper.ConsumerEntityToDto(p));
    return pagedResult;
  }

  @Override
  public Consumer getConsumerById(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("consumerId", "Consumer Not found"));
  }

	@Override
	public ResponseEntity<ApiResponse> deleteById(Long id) {
		repository.deleteById(id);
		return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted category"), HttpStatus.OK);
	}

	@Override
	public Consumer addConsumer(ConsumerDto consumer) {
		// TODO Auto-generated method stub
		return null;
	}

}
