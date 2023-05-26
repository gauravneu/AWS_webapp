package com.example.awscloudproject.controller;

import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserDetailService userDetailService;

    @PostMapping("/saveUser")
    public String saveUser(@RequestBody UserDto user) {
        String userId = userDetailService.saveUser(user);
        return userId;
    }
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/getUser")
    public String getUser(@RequestParam String email)  {
        System.out.println(userDetailService.getUserEntityByEmail(email));
        if(!userDetailService.getUserEntityByEmail(email).isEmpty()){
            return "User Found";
        }
        return "User Not Found";
    }
}
