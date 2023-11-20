package com.cg.inventorydoctorderservice.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.cg.inventorydoctorderservice.entity.Status;
import com.cg.inventorydoctorderservice.service.utils.IntegerListConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePrescriptionAppointmentRequest {
	
    private Integer id;
    
    @JsonProperty("prescription")
    private JsonNode  prescription; 
}
