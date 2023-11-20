package com.cg.inventoryauthservice.entity;

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
    
    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;

    private Date dateOfBirth;

    private String gender;

    private String email;

    private String phoneNumber;

    private String address;

    private Boolean status;

    @NotNull
    private String username;
    
    @NotNull
    private String password;

    private String avatar;

    private Date created_at;
    
    private Date update_at;
      
}

