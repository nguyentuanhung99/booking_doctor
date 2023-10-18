
package com.cg.authservice.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.authservice.dto.RegisterRequest;
import com.cg.authservice.dto.UpdateRequest;
import com.cg.authservice.dto.UserDetailsDto;
import com.cg.authservice.service.AuthService;

import lombok.AllArgsConstructor;

@RequestMapping("/register")
@RestController
@AllArgsConstructor
@CrossOrigin(origins =  "*") 
public class RegisterController {

  private final AuthService authService;

  @PostMapping
  public ResponseEntity<UserDetailsDto> register(@Valid @RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
  }

  @PutMapping
  public ResponseEntity<Map<String, String>> updateUser(@Valid @RequestBody UpdateRequest updateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.updateUser(updateRequest));
  }

  @GetMapping
  public ResponseEntity<List<UserDetailsDto>> fetchAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(authService.fetchAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDetailsDto> fetchUserById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.fetchUserById(id));
  }

  
}
