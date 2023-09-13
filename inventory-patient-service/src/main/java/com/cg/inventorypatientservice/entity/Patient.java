package com.cg.inventorypatientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Patient")
@Data
@Entity
@Builder
public class Patient {

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

    private Date created_at;
    
    private Date update_at;
      
}

