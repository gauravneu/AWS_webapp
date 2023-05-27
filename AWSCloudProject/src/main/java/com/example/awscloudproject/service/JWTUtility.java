package com.example.awscloudproject.service;

import com.example.awscloudproject.exception.CloudProjectException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@NoArgsConstructor
public class JWTUtility {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createtoken(claims, userName);
    }

    private String createtoken(Map<String, Object> claims, String userName) {
        return Jwts.builder().setClaims(claims).setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public String extractUserName(String token) /*throws CloudProjectException */{
      /*  if(validateToken(token,null))*/
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
   /*     else throw new MalformedJwtException("Malformed");*/
    }

    public boolean validateToken(String token, UserDetails userDetails) /*throws CloudProjectException */{
            /*try{*/
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
      /*  } catch (Exception ex) {
            throw new CloudProjectException("JWT Token malformed",ex);
        }*/
    }
}
