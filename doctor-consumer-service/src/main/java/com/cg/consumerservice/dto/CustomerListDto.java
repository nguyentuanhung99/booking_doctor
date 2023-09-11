
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
public class CustomerListDto {

	private Long leadId;
	private String fullName;
	private String status;
	private BigDecimal totalPrice;
	private String phoneNumber;
	private String email;
	private LocalDate dateAppointment;
}
