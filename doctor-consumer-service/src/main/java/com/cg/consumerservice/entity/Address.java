
package com.cg.consumerservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Table - A table with the entity structure will be created
 * @Getter - Creates getters
 * @Setter - Creates Setter Objectss
 * @NoArgsConstuctor - Creates a no argument constructor
 * @ToString - Overrides default string behaviour
 */
@Entity
@Table(name = "lead_address")
@Getter
@Setter
@NoArgsConstructor
@ApiModel("Address - Stores address of the consumer")
public class Address {

    @Id
    @Column(name = "address_id")
    @SequenceGenerator(name = "lead_address_id_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "lead_address_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer addressId;

    @Column(name = "is_primary_address", columnDefinition = "TINYINT", length = 1)
    private Integer isPrimaryAdress;
    
    @ApiModelProperty("City")
    @Column(name = "city", length = 100)
    private String city;

    @ApiModelProperty("Area")
    @Column(name = "area", length = 155)
    private String area;

    @ApiModelProperty("Company")
    @Column(name = "company", length = 50)
    private String company;
    
    @ApiModelProperty("Country")
    @Column(name = "country", length = 50)
    private String country;
    
    @ApiModelProperty("Address Detail")
    @Column(name = "detail", length = 250)
    private String addressDetail;
    
    @ApiModelProperty("Notes")
    @Column(name = "note", length = 500)
    private String addressNote;
    
    @ApiModelProperty("Longitude")
    @Column(name = "map_longitude", length = 150)
    private String longitude;
    
    @ApiModelProperty("Latitude")
    @Column(name = "map_latitude", length = 150)
    private String latitude;
    
    @ManyToOne
    @JoinColumn(name = "lead_consumer", referencedColumnName = "lead_id", foreignKey = @ForeignKey(name = "FK_ADDRESS_LEAD_ID"))
    private Consumer consumer;
    
}
