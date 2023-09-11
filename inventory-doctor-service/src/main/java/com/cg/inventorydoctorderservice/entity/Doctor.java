package com.cg.inventorydoctorderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Doctor")
@Data
@Entity
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String Role;

    private Date dateOfBirth;

    private String gender;

    private String email;

    private String phoneNumber;

    private String address;

    private Boolean status;

    private String password;

    private String avatar;

    private String biography;    
    
    private String serviceName;
    
    private Integer departmentId;
    
    private String specialization;
    
    private Date booking_time;
    
    private Date created_at;
    
    private Date update_at;
      
}

