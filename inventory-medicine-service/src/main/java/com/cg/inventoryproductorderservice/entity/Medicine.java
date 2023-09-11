package com.cg.inventoryproductorderservice.entity;

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
@Table(name = "Medicine")
@Builder
@Entity
public class Medicine {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Integer id;
	
	@Column(length = 100)
    private String name;
	
	@Column(length = 100)
    private String image;
	
	@Column(length = 100)
    private String price;
	
	@Column(length = 100)
    private String description;
	
    private Date create_at;
    
    private Date update_at;
    
    private Boolean status;
}
