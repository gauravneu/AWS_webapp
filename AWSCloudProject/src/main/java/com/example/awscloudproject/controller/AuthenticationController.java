package com.example.awscloudproject.controller;

import com.example.awscloudproject.dto.AuthRequest;
import com.example.awscloudproject.service.JWTService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthenticationController {
  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/authenticate")
  public String authentication(@Valid @RequestBody(required = true) AuthRequest authRequest) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  authRequest.getUserName(), authRequest.getPassword()));
      if (authentication.isAuthenticated()) {
        return jwtService.generateToken(authRequest.getUserName());
      } else {
        throw new BadCredentialsException("Authentication Failure");
      }
    } catch (BadCredentialsException ex) {
      throw new BadCredentialsException("Invalid Credentials");
    }
  }
}
