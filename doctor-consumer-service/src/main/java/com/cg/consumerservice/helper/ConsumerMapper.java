
package com.cg.consumerservice.helper;

import com.cg.consumerservice.dto.ConsumerDto;
import com.cg.consumerservice.dto.CustomerListDto;
import com.cg.consumerservice.entity.Consumer;

public class ConsumerMapper {

	public static Consumer DtoToConsumerEntity (Consumer request) {
	    Consumer con = new Consumer();
	      con.setLeadId(request.getLeadId());
	      con.setFullName(request.getFullName());
	      con.setStatus(request.getStatus());
	      con.setTotalPrice(request.getTotalPrice());
		return con;
	  }
	
  public static CustomerListDto ConsumerEntityToDto (Consumer consumerDto) {
    return CustomerListDto.builder()
      .leadId(consumerDto.getLeadId())
      .fullName(consumerDto.getFullName())
      .status(consumerDto.getStatus())
      .totalPrice(consumerDto.getTotalPrice())
      .build();
  }

}
