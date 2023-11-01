package com.cg.inventorypatientservice.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cg.inventorypatientservice.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequest {
	
    private Integer id;

    private String text;

    private Date start;
    
    private Date end;

    private Integer participants;

    private Boolean resizeDisabled;

    private Boolean moveDisabled;

    private Date created_at;
    
    private Date update_at;
    
    private Status status; 
    
    private Integer idDoctor;
    
    private Integer idPatient;
}
