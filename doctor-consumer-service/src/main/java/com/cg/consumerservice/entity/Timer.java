package com.cg.consumerservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "lead_timer_alarm")
@Getter
@Setter
@NoArgsConstructor
@ApiModel("Leave history : Stores the history of leaves of employee")
public class Timer {

    /**
     * Manual sequence generator for leave history
     * Starting value is 100000
     */
    @Id
    @Column(name = "timer_id")
    @SequenceGenerator(name = "lead_timer_id_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "lead_timer_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer timerId;

    
    @Column(name = "is_active_appointments")
    private Integer isActiveAppointments;
    
    @Column(name = "is_notification")
    private Integer isNotification;
    
    @Column(name = "date_appointment")
    private LocalDate dateAppointment;
    
    @Column(name = "date_expiry_appointment")
    private LocalDate dateAppointmentExpiry;
    
    @Column(name = "time_appointment")
    private LocalDate timeAppointment;

    
    @ManyToOne
    @JoinColumn(name = "lead_consumer", referencedColumnName = "lead_id", foreignKey = @ForeignKey(name = "FK_TIMER_LEAD_ID"))
    private Consumer consumer;
    
}
