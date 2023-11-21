package com.example.awscloudproject.service;

import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.exception.AlreadyExistingUserException;
import com.example.awscloudproject.exception.UserDoesNotExistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService extends UserDetailsService {

  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  String getEmailFromMDC();

  UserDto getUserEntityByEmail(String email) throws UserDoesNotExistException;

  UserDto updateUser(UserDto userDto);

  UserDto saveUser(UserDto userDto) throws AlreadyExistingUserException;
}
