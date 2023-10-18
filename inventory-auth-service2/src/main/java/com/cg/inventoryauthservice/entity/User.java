package com.cg.inventoryauthservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.cg.inventoryauthservice.service.utils.StringListConverter;


@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user")
@Data
@Entity
@Builder
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String fullName;

    private String avatar;

    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
