package com.cg.inventorydoctorderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cg.inventorydoctorderservice.service.utils.StringListConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Appointment")
@Data
@Entity
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    private Date start;
    
    private Date end;

    private Integer participants;
    
    private Integer amount;

    private Boolean resizeDisabled;

    private Boolean moveDisabled;

    private Date created_at;
    
    private Date update_at;
    
    private Status status; 
    
    private Integer idDoctor;
    
    private Integer idPatient;
    
    private String reason;
    
    private Boolean Trangthai;
      
    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> listPrescription;
    
	public boolean isValidLesson(Appointment appointment) {
        if (appointment == null) {
            // No previous lesson, so the new lesson is always valid
            return true;
        } else {
            // Check if the start and end dates overlap with the previous lesson
            if (start.after(appointment.getEnd()) || end.before(appointment.getStart())) {
                // No overlap, so the new lesson is valid
                return true;
            } else {
                // Overlap exists, the new lesson is not valid
                return false;
            }
        }
   }
}

