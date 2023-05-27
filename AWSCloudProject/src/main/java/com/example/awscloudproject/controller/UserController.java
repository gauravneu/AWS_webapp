package com.example.awscloudproject.controller;

import com.example.awscloudproject.dto.AuthRequest;
import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.exception.CloudProjectException;
import com.example.awscloudproject.service.JWTUtility;
import com.example.awscloudproject.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    @PostMapping("/saveUser")
    public String saveUser(@RequestBody UserDto user) {
        String userId = userDetailService.saveUser(user);
        return userId;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"))
    @GetMapping("/getUser")
    public String getUser(@RequestParam String email) {
        System.out.println(userDetailService.getUserEntityByEmail(email));
        if (!userDetailService.getUserEntityByEmail(email).isEmpty()) {
            return "User Found";
        }
        return "User Not Found";
    }

    @PostMapping("/authenticate")
    public String authentication(@RequestBody AuthRequest authRequest) throws CloudProjectException {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                            authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtUtility.generateToken(authRequest.getUserName());
            } else {
                throw new UsernameNotFoundException("Invalid User Request");
            }
        }
        catch(Exception ex){
            throw new CloudProjectException("Cloud Ex" ,new RuntimeException());
        }

    }
}
