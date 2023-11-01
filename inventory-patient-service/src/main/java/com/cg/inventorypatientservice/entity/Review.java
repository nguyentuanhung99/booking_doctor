package com.cg.inventorypatientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Review")
@Data
@Entity
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer star;
    
    private String title;
    
    private String description;
    
    private String namePatient;
    
    private Integer idPatient;
    
    private Integer idDoctor;
    
    private Integer idReview;
    
    private Date created_at;
      
}

