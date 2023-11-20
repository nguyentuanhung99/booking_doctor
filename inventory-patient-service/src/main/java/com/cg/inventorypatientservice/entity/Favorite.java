package com.cg.inventorypatientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cg.inventorypatientservice.service.utils.IntegerListConverter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Favorite")
@Data
@Entity
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    private Integer idPatient;
    
    @Lob
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> listIdDoctor;
    
      
}

