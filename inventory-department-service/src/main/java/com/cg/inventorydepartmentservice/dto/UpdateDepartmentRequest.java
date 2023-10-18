package com.cg.inventorydepartmentservice.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepartmentRequest {
	
    private Integer id;
    
    private String name;
    
    private String address;
    
    private Date created_at;
    
    private Date update_at;
}
