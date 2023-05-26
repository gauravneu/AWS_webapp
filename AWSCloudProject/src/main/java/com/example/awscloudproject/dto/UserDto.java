package com.example.awscloudproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<String> roles;
}







