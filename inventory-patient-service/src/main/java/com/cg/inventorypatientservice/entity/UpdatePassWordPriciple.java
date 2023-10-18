package com.cg.inventorypatientservice.entity;

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
