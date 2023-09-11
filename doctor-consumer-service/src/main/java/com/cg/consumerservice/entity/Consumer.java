
package com.cg.consumerservice.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cg.consumerservice.enums.Gender;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lead_consumer")
public class Consumer {
	
  @Id
  @Column(name = "lead_id")
  @SequenceGenerator(name = "lead_consumer_id_sequence", initialValue = 100000, allocationSize = 1)
  @GeneratedValue(generator = "lead_consumer_id_sequence", strategy = GenerationType.SEQUENCE)
  private Long leadId;
  
  @Column(name = "full_name", length = 191)
  private String fullName;
  @Column(name = "current_plan", length = 191)
  private String currentPlan;
  @Column(name = "current_project", length = 191)
  private String currentProject;
  
  
  @Column(name = "status", length = 10)
  private String status;
  @Column(name = "attitude", length = 100)
  private String attitude;
  @Column(name = "rating", length = 2)
  private BigDecimal rating;
  
  
  @Column(name = "is_public", columnDefinition = "TINYINT", length = 1)
  private Integer isPublic;
  @Column(name = "is_responsible", columnDefinition = "TINYINT", length = 1)
  private Integer isResponsible;
  @Column(name = "is_price_bargaining", columnDefinition = "TINYINT", length = 1)
  private Integer isPriceBargaining;
  @Column(name = "is_price_crosssell", columnDefinition = "TINYINT", length = 1)
  private Integer isPriceCrossSell;
  @Column(name = "is_price_need", columnDefinition = "TINYINT", length = 1)
  private Integer isPriceNeed;
  @Column(name = "total_price")
  private BigDecimal totalPrice;
  
  
  @Column(name = "assigned", length = 10)
  private Integer assigned;
  @Column(name = "action", length = 10)
  private String action;
  
  
  @Column(name = "gender", length = 1)
  @Enumerated(EnumType.STRING)
  @ApiModelProperty("Gender")
  private Gender gender;
  
  
  @Column(name = "avatar", length = 191)
  private String avatar;

  
  @Column(name = "lost", columnDefinition = "TINYINT", length = 1)
  private Integer lost;
  @Column(name = "lost_reason", length = 191)
  private String lostReason;
  
}
