
package com.cg.authservice.service;

import java.util.List;
import java.util.Map;

import com.cg.authservice.dto.ForgotPasswordRequest;
import com.cg.authservice.dto.LoginRequest;
import com.cg.authservice.dto.LoginResponse;
import com.cg.authservice.dto.RegisterRequest;
import com.cg.authservice.dto.UpdateRequest;
import com.cg.authservice.dto.UserDetailsDto;

public interface AuthService {
  LoginResponse login(LoginRequest loginRequest);

  UserDetailsDto register(RegisterRequest registerRequest);

  Map<String, String> updateUser(UpdateRequest updateRequest);

  List<UserDetailsDto> fetchAllUsers();

  UserDetailsDto fetchUserById(Long id);

  boolean checkIfUsernameExists(String username);

  Map<String, String> fetchSecurityQuestionForUser(String username);

  Map<String, String> validateAnswerAndUpdate(ForgotPasswordRequest forgotPasswordRequest);
}
