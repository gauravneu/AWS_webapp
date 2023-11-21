package com.example.awscloudproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
  @NotBlank(message = "userName is mandatory")
  private String userName;

  @NotBlank(message = "password is mandatory")
  private String password;
}
