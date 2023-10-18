
package com.cg.authservice.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {

  private Long userId;
  @Size(min = 5, max = 20)
  private String username;
  private String role;
  @Pattern(regexp = "^(Male|Female)$", message = "Gender must be Male or Female")
  private String gender;
  @Email
  private String email;
  @Size(min = 10, max = 10, message = "Length of phone number must be 10")
  private String phoneNo;
  @Valid
  private String firstName;
  @Size(min = 5, max = 30)
  private String lastName;

}
