package com.cg.consumerservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cg.consumerservice.enums.LeaveStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Gagandeep
 * @date 07-05-2020
 * @time 19:54
 */

 /**
  * Creates a grade table
  */
@Entity
@Table(name = "lead_history")
@Getter
@Setter
@NoArgsConstructor
@ApiModel("Leave history : Stores the history of leaves of employee")
public class History {

    /**
     * Manual sequence generator for leave hostory
     * Starting value is 100000
     */
    @Id
    @Column(name = "history_id")
    @SequenceGenerator(name = "lead_history_id_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "lead_history_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer historyId;
    
    @Column(name = "last_change_by")
    private Integer lastChangeBy;
    
    @Column(name = "date_assigned")
    @ApiModelProperty("Active to time")
    private LocalDate dateAssigned;
    
    @Column(name = "last_status", columnDefinition = "varchar(20) check (leave_status in ('Applied','Approved','Rejected'))")
    @Enumerated(EnumType.STRING)
    private LeaveStatus lastStatus;
    
    @Column(name = "last_seller_action", length = 20)
    private String lastSellerAction;
    
    @Column(name = "created_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime creationTime;
    
    @Column(name = "updated_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    
    
    @Column(name = "num_of_appointments", length = 20)
    private Integer numAppointments;
    
    
    @Column(name = "num_of_bargain", length = 20)
    private Integer numBargain;
    
    @Column(name = "created_time_bargain", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime creationTimeBargain;
    
    @Column(name = "updated_time_bargain", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updateTimeBargain;
    
    
    @ManyToOne
    @JoinColumn(name = "lead_consumer", referencedColumnName = "lead_id", foreignKey = @ForeignKey(name = "FK_HISTORY_LEAD_ID"))
    private Consumer consumer;
    
    
}
