package com.cg.inventorydoctorderservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Specialities")
@Builder
@Entity
public class Specialities {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Integer id;
	
	private String nameSpecialities;
	
	private String image;
	
}
