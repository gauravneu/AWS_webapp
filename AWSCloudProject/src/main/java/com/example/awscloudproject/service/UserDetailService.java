package com.example.awscloudproject.service;

import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.exception.AlreadyExistingUserException;
import com.example.awscloudproject.exception.UserDoesNotExistException;
import com.example.awscloudproject.model.UserEntity;
import com.example.awscloudproject.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> opt = userRepository.findUserByEmail(username);
    if (opt.isEmpty()) {
      throw new UsernameNotFoundException(
          "UserEntity with user name : " + username + " not found !");
    } else {
      UserEntity userEntity = opt.get();
      return new User(
          userEntity.getEmail(),
          userEntity.getPassword(),
          Stream.of("Role1")
              .map(role -> new SimpleGrantedAuthority(role))
              .collect(Collectors.toSet()));
    }
  }

  public String getEmailFromMDC() {
    String userNameFromMDC = "";
    for (Map.Entry<String, String> userFromMDC : MDC.getCopyOfContextMap().entrySet()) {
      userNameFromMDC = userFromMDC.getKey();
    }
    return userNameFromMDC;
  }

  public UserDto getUserEntityByEmail(String email) throws UserDoesNotExistException {
    Optional<UserEntity> opt = userRepository.findUserByEmail(email);
    if (opt.isPresent()) {
      return UserDto.builder()
          .id(opt.get().getUserId())
          .username(opt.get().getEmail())
          .firstName(opt.get().getFirstName())
          .lastName(opt.get().getLastName())
          .accountUpdated(opt.get().getAccountUpdated())
          .accountCreated(opt.get().getAccountCreated())
          .build();
    }
    throw new UserDoesNotExistException("User Does Not Exist");
  }

  public UserDto updateUser(UserDto userDto) {
    Optional<UserEntity> ue1 = userRepository.findUserByEmail(userDto.getUsername());
    ue1.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
    ue1.get().setFirstName(userDto.getFirstName());
    ue1.get().setLastName(userDto.getLastName());
    UserEntity ue2 = userRepository.save(ue1.get());

    return UserDto.builder()
        .id(ue2.getUserId())
        .firstName(ue2.getFirstName())
        .lastName(ue2.getLastName())
        .username(ue2.getEmail())
        .accountCreated(ue2.getAccountCreated())
        .accountUpdated(ue2.getAccountUpdated())
        .build();
  }

  public UserDto saveUser(UserDto userDto) throws AlreadyExistingUserException {

    Optional<UserEntity> userEntity1 = userRepository.findUserByEmail(userDto.getUsername());
    if (userEntity1.isPresent()) {
      throw new AlreadyExistingUserException("User Already Exists");
    }
    String encodedPassword = passwordEncoder.encode(userDto.getPassword());

    UserEntity userEntity =
        new UserEntity()
            .builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .email(userDto.getUsername())
            .password(encodedPassword)
            .build();

    userEntity = userRepository.save(userEntity);

    return UserDto.builder()
        .id(userEntity.getUserId())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .username(userEntity.getEmail())
        .accountCreated(userEntity.getAccountCreated())
        .accountUpdated(userEntity.getAccountUpdated())
        .build();
  }
}
