package com.cg.inventorydoctorderservice.entity;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
	  ROLE_ADMIN, ROLE_CLIENT, ROLE_DOCTOR;

	  public String getAuthority() {
	    return name();
	  }
}
