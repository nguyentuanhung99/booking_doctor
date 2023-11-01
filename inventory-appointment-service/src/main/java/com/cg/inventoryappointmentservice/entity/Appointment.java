package com.cg.inventoryappointmentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    private LocalDateTime start;
    
    private LocalDateTime end;

    private Integer participants;

    private Boolean resizeDisabled;

    private Boolean moveDisabled;

    private Date created_at;
    
    private Date update_at;
    
    private Status status; 
      
}

