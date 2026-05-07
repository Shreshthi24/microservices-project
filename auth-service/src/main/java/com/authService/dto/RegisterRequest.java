package com.authService.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
  //  @NotBlank(message = "Email is required")
    //@Email(message = "Invalid email format")
    private String email;

    //@NotBlank(message = "Password is required")
   // @Size(min = 6, max = 15,
         //   message = "Password must be between 6 and 15 characters")
    private String password;
}