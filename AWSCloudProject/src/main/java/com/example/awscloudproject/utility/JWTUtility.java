package com.example.awscloudproject.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@UtilityClass
public class JWTUtility {
  private static Key getKey() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }

  public static String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  private static String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(getKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public static String extractUserName(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
    return true;
  }
}
