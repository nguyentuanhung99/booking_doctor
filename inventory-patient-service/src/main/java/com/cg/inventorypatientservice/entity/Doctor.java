package com.cg.inventorypatientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import com.cg.inventorypatientservice.service.utils.IntegerListConverter;
import com.cg.inventorypatientservice.service.utils.StringListConverter;

import java.util.Date;
import java.util.List;

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
    
    private String description;

    private Date dateOfBirth;

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
    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> serviceName;
    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> specialization;
    
    @Lob
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> education;
    
    @Lob
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> experience;
    
    @Lob
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> awards;  
    
    private Integer CountReview;
    
    private Integer CountStar;
    
    private Date created_at;
    
    private Date update_at;
    
    private Boolean status;

    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;
      
}

