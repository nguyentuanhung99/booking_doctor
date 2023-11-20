package com.cg.inventoryauthservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateSpecialitiesRequest {
	
    private Integer id;

    private String nameSpecialities;

}
