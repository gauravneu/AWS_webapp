package com.example.awscloudproject.controller;

import com.example.awscloudproject.dto.AuthRequest;
import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.exception.CloudProjectException;
import com.example.awscloudproject.model.UserEntity;
import com.example.awscloudproject.service.JWTUtility;
import com.example.awscloudproject.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user, HttpServletResponse response) throws IOException {
        if (userDetailService.getUserEntityByEmail(user.getUsername()).isPresent()) {
            log.info("Bad User");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            UserDto ud1 = userDetailService.saveUser(user);
            return new ResponseEntity<>(ud1, HttpStatus.OK);
        }
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"))
    @GetMapping("/self")
    public UserDto getSelf() {
        String userNameFromMDC = "";
        for (Map.Entry<String, String> us : MDC.getCopyOfContextMap().entrySet()) {
            userNameFromMDC = us.getKey();
        }
        Optional<UserEntity> optUE = userDetailService.getUserEntityByEmail(userNameFromMDC);
        return UserDto.
                builder().
                id(optUE.get().getUserId())
                .firstName(optUE.get().getFirstName())
                .lastName(optUE.get().getLastName())
                .username(optUE.get().getEmail())
                .accountCreated(optUE.get().getAccountCreated())
                .accountUpdated(optUE.get().getAccountUpdated())
                .build();

    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"))
    @GetMapping("/getUser")
    public String getUser(@RequestParam String email) {
        if (userDetailService.getUserEntityByEmail(email).isPresent()) {
            System.out.println(MDC.getCopyOfContextMap());
            return "User Found";
        }
        return "User Not Found";
    }

    @PostMapping("/authenticate")
    public String authentication(@RequestBody AuthRequest authRequest) throws CloudProjectException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                            authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtUtility.generateToken(authRequest.getUserName());
            } else {
                throw new UsernameNotFoundException("Invalid User Request");
            }
        } catch (Exception ex) {
            throw new CloudProjectException("Cloud Ex", new RuntimeException());
        }

    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"))
    @PutMapping("/self")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        String userNameFromMDC = "";
        for (Map.Entry<String, String> us : MDC.getCopyOfContextMap().entrySet()) {
            userNameFromMDC = us.getKey();
        }
        if (!userNameFromMDC.equals(userDto.getUsername())) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else if (userDto.getUsername() == null
                ||
                userDto.getPassword() == null
                ||
                userDto.getFirstName() == null
                ||
                userDto.getLastName() == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            UserDto ud1 = userDetailService.updateUser(userDto);
            return new ResponseEntity<>(ud1, HttpStatus.OK);
        }
    }
}
