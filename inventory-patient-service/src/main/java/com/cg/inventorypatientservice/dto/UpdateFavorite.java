package com.cg.inventorypatientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFavorite {

    private Boolean check;
    
    private Integer idDoctor;

    
}
