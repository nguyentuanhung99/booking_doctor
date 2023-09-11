
package com.cg.consumerservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ConsumerDto {
	
	private Long leadId;
	private String fullName;
	private String status;
	private String attitude;
	private String avatar;
	  
	private Long contactId; 
	private Integer phoneNumber;
	private String email;
	  
	private BigDecimal totalPrice;
	  
	private LocalDate timeAppointment;
	  
}
