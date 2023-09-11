package com.cg.inventoryproductorderservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateMedicineRequest {
	
    private Integer id;

    private String name;

    private String image;

    private String price;

    private String description;

    private Date create_at;

    private Date update_at;
    
    private Boolean status; 
}
