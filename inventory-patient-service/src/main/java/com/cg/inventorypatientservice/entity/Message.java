package com.cg.inventorypatientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cg.inventorypatientservice.service.utils.IntegerListConverter;
import com.cg.inventorypatientservice.service.utils.StringListConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Message")
@Data
@Entity
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idSent;
    
    private Integer idReceive;

    private String typeSent;
    
    private String typeReceive;
    
    private String content;
    
    private Date dateTime;
    
     
}

