package com.cg.inventorydoctorderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.inventorydoctorderservice.entity.Doctor;
import com.cg.inventorydoctorderservice.repository.DoctorRepository;


@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  private final DoctorRepository doctorRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Doctor appUser = doctorRepository.findByUsername(username);

    if (appUser == null) {
      throw new UsernameNotFoundException("Doctor '" + username + "' not found");
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
