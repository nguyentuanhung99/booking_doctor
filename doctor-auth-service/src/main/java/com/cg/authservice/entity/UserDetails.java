
package com.cg.authservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cg.authservice.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_details")
public class UserDetails {

  @Id
  @Column(name = "user_details_id")
  private Long userDetailsId;

  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Column(name = "first_name", length = 40)
  private String firstName;
  @Column(name = "last_name", length = 40)
  private String lastName;
  @Column(name = "email", length = 50)
  private String email;
  @Column(name = "phone_no", length = 10)
  private String phoneNo;
  
  @Column(name = "security_question", length = 80)
  private String securityQuestion;
  @Column(name = "security_answer", length = 10)
  private String securityAnswer;
  

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_details_Id", referencedColumnName = "user_Id")
  @MapsId
  private User user;

}
