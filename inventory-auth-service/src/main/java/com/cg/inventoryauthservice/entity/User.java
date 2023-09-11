/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-19 18:18:55
 * @modify date 2020-09-19 18:18:55
 * @desc User Credentials
 */
package com.cg.inventoryauthservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

  @Id
  @SequenceGenerator(name = "user_id_sequence", initialValue = 100000, allocationSize = 1)
  @GeneratedValue(generator = "user_id_sequence", strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id", unique = true)
  private Integer userId;
  
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "role")
  private String role;
}
