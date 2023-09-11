package com.cg.consumerservice.entity;

import java.math.BigDecimal;

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

import org.apache.poi.hpsf.Decimal;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Table Name - Employee Details
 * @Getter - Generates getters
 * @Setter - Generates Setter
 * @ToString - Overrides default string behviour
 */
@Entity
@Table(name = "lead_price_crossell")
@Getter
@Setter
@NoArgsConstructor
@ApiModel("Consumer lead contact details : Stores multiple phone or email")
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class PriceCrossSell {

    @Id
    @Column(name = "price_id")
    @SequenceGenerator(name = "lead_price_crossell_id_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "lead_price_crossell_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer priceId;
    
    
    @Column(name = "wish_price", length = 50)
    private BigDecimal wishPrice;

    @Column(name = "wish_product", length = 250)
    private String wishProduct;
    
    @Column(name = "wish_quantity", length = 10)
    private Integer wishQuantity;
    
    @Column(name = "wish_quality", length = 10)
    private String wishQuality;

    
    @Column(name = "discount_percent")
    private Double discountPercent;
    
    @ManyToOne
    @JoinColumn(name = "lead_consumer", referencedColumnName = "lead_id", foreignKey = @ForeignKey(name = "FK_PRICE_CROSSSELL_LEAD_ID"))
    private Consumer consumer;


}
