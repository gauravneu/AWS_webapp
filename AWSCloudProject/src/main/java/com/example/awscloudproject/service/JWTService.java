package com.example.awscloudproject.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
  String generateToken(String userName);

  String createToken(Map<String, Object> claims, String userName);

  public String extractUserName(String token);

  public boolean validateToken(String token, UserDetails userDetails);
}
