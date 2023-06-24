package com.example.awscloudproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String accountCreated;
    private String accountUpdated;
    private List<String> roles;
}







