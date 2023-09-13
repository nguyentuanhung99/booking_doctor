package com.cg.inventorypatientservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updatePatientRequest {
	
    private Integer id;

    private String firstName;

    private String lastName;

    private String role;

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
