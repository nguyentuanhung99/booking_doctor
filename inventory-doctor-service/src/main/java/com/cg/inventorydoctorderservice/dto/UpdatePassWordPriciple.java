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
public class UpdatePassWordPriciple {

    private String oldPassword;
    
    private String newPassword;
    
    private String repeatNewPassword;

    
}
