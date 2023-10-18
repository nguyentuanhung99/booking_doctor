package com.cg.inventorypatientservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.inventorypatientservice.entity.Patient;
import com.cg.inventorypatientservice.repository.PatientRepository;


@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  private final PatientRepository patientRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Patient appUser = patientRepository.findByUsername(username);

    if (appUser == null) {
      throw new UsernameNotFoundException("Patient '" + username + "' not found");
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
