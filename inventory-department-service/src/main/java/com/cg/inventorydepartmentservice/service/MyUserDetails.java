package com.cg.inventorydepartmentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.inventorydepartmentservice.entity.Admin;
import com.cg.inventorydepartmentservice.repository.AdminRepository;


@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  private final AdminRepository patientRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Admin appUser = patientRepository.findByUsername(username);

    if (appUser == null) {
      throw new UsernameNotFoundException("Admin '" + username + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(appUser.getPassword())//
        .authorities(appUser.getAppUserRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
