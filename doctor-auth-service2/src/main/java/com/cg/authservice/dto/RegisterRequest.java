
package com.cg.authservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
public class RegisterRequest {

  @NotBlank
  @Size(min = 5, max = 20)
  private String username;
  @NotBlank
  @Size(min = 5, max = 20)
  private String password;
  @NotBlank
  @Pattern(regexp = "^(Male|Female)$", message = "Gender must be Male or Female")
  private String gender;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  @Size(min = 10, max = 10, message = "Length of phone number must be 10")
  private String phoneNo;
  @NotBlank
  @Size(min = 5, max = 30)
  private String firstName;
  @NotBlank
  @Size(min = 5, max = 30)
  private String lastName;

}
