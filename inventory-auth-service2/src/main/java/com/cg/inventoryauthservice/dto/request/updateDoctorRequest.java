package com.cg.inventoryauthservice.dto.request;

import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.cg.inventoryauthservice.entity.AppUserRole;
import com.cg.inventoryauthservice.service.utils.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateDoctorRequest {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String description;
    
    private String gender;

    private String email;

    private String phoneNumber;
    
    private String username;

    private String password;

    private String avatar;

    private Integer specialityId;    
    
    private String address;
    
    private Integer departmentId;
   
    private Integer price;
    
    private List<String> serviceName;
    
    private List<String> specialization;
    
    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> education;
    
    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> experience;
    
    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> awards;  
    
    private Date created_at;
    
    private Date update_at;
    
    private Boolean status;

    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;
}
