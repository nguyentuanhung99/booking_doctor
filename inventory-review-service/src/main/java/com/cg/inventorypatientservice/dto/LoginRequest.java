package com.cg.inventoryreviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Không được bỏ trống mục này")
    private String username;

    @NotEmpty(message = "Không được bỏ trống mục này")
    private String password;

}
