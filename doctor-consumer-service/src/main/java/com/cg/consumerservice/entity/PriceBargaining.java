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
@Table(name = "lead_price_bargaining")
@Getter
@Setter
@NoArgsConstructor
@ApiModel("Consumer lead contact details : Stores multiple phone or email")
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class PriceBargaining {

    @Id
    @Column(name = "price_bargain_id")
    @SequenceGenerator(name = "lead_price_id_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "lead_price_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer priceId;
    
    @Column(name = "is_cross_sell", columnDefinition = "TINYINT", length = 1)
    private Integer isCrossSell;

    
    @Column(name = "status", length = 5)
    private String status;
    
    @Column(name = "bargaining_price", length = 50)
    private BigDecimal bargainingPrice;

    @Column(name = "bargaining_product", length = 250)
    private String bargainingProduct;
    
    @Column(name = "bargaining_quantity", length = 10)
    private Integer bargainingQuantity;
    
    @Column(name = "bargaining_quality", length = 10)
    private String bargainingQuality;
    
    
    @Column(name = "threshold_price", length = 50)
    private BigDecimal thresholdPrice;
    
    @Column(name = "threshold_product", length = 150)
    private String thresholdProduct;
    
    @Column(name = "threshold_quantity", length = 10)
    private Integer thresholdQuantity;
    
    @Column(name = "threshold_quality", length = 10)
    private String thresholdQuality;
    
    
    @Column(name = "discount_percent")
    private Double discountPercent;
    
    @ManyToOne
    @JoinColumn(name = "lead_consumer", referencedColumnName = "lead_id", foreignKey = @ForeignKey(name = "FK_PRICE_BARGAIN_LEAD_ID"))
    private Consumer consumer;


}
