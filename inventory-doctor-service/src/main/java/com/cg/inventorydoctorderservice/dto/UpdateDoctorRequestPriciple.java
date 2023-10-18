package com.cg.inventorydoctorderservice.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.cg.inventorydoctorderservice.entity.AppUserRole;
import com.cg.inventorydoctorderservice.entity.Awards;
import com.cg.inventorydoctorderservice.entity.Education;
import com.cg.inventorydoctorderservice.entity.Experience;
import com.cg.inventorydoctorderservice.service.utils.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctorRequestPriciple {

    private String firstName;

    private String lastName;

    private String description;
    
    private Date dateOfBirth;

    private String gender;

    private String email;

    private String phoneNumber;
    
    private String avatar;

    private Integer specialityId;    
    
    private String address;
    
    private Integer departmentId;
   
    private Integer price;
    
    private List<String> serviceName;
    
    private List<String> specialization;
    
    private List<Education> education;
    
    private List<Experience> experience;
    
    private List<Awards> awards;
    
}
