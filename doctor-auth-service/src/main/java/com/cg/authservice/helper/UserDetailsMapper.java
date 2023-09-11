
package com.cg.authservice.helper;

import com.cg.authservice.dto.RegisterRequest;
import com.cg.authservice.dto.UpdateRequest;
import com.cg.authservice.dto.UserDetailsDto;
import com.cg.authservice.entity.User;
import com.cg.authservice.entity.UserDetails;
import com.cg.authservice.enums.Gender;

public class UserDetailsMapper {

  public static UserDetails registerToUserDetails(RegisterRequest registerRequest) {
    User user = User.builder()
      .username(registerRequest.getUsername())
      .password(registerRequest.getPassword())
      .role("User")
      .build();
    return UserDetails.builder()
      .user(user)
      .firstName(registerRequest.getFirstName())
      .lastName(registerRequest.getLastName())
      .securityQuestion("Default Question")
      .securityAnswer("answer")
      .gender(Gender.valueOf(registerRequest.getGender()))
      .email(registerRequest.getEmail())
      .phoneNo(registerRequest.getPhoneNo())
      .build();
  }

  public static UserDetailsDto userDetailsToDto(UserDetails userDetails){
    return UserDetailsDto.builder()
      .userId(userDetails.getUserDetailsId())
      .username(userDetails.getUser().getUsername())
      .role(userDetails.getUser().getRole())
      .phoneNo(userDetails.getPhoneNo())
      .gender(userDetails.getGender().toString())
      .email(userDetails.getEmail())
      .firstName(userDetails.getFirstName())
      .lastName(userDetails.getLastName())
      .build();
  }

  public static UserDetails updateRequestToUserDetails(UpdateRequest updateRequest) {
    return UserDetails.builder()
      .firstName(updateRequest.getFirstName())
      .lastName(updateRequest.getLastName())
      .userDetailsId(updateRequest.getUserId())
      .phoneNo(updateRequest.getPhoneNo())
      .gender(Gender.valueOf(updateRequest.getGender()))
      .email(updateRequest.getEmail())
      .securityQuestion(updateRequest.getSecurityQuestion())
      .securityAnswer(updateRequest.getSecurityAnswer())
      .build();
  }

}
