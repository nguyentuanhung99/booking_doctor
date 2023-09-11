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
@Table(name = "lead_contact")
@Getter
@Setter
@NoArgsConstructor
@ApiModel("Consumer lead contact details : Stores multiple phone or email")
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Contact {

    @Id
    @Column(name = "contact_id")
    @SequenceGenerator(name = "lead_contact_id_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "lead_contact_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long contactId;
    
    @Column(name = "is_primary_contact", columnDefinition = "TINYINT", length = 1)
    private Integer isPrimaryContact;

    @Column(name = "source", length = 100)
    private String source;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phoneno", length = 20)
    private String phoneNumber;
    
    @Column(name = "social_link", length = 250)
    private String socialLink;


    @ManyToOne
    @JoinColumn(name = "lead_consumer", referencedColumnName = "lead_id", foreignKey = @ForeignKey(name = "FK_CONTACT_LEAD_ID"))
    private Consumer consumer;
}
