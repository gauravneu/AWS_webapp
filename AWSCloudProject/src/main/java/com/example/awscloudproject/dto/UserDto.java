package com.example.awscloudproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
  private String id;

  @NotBlank(message = "firstName is mandatory")
  private String firstName;

  @NotBlank(message = "lastName is mandatory")
  private String lastName;

  @NotBlank(message = "password is mandatory")
  private String password;

  @NotBlank(message = "Username is mandatory")
  private String username;

  private String accountCreated;
  private String accountUpdated;
}
