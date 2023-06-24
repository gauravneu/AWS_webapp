package com.example.awscloudproject.service;

import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.model.UserEntity;
import com.example.awscloudproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> opt = userRepository.findUserByEmail(username);
        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("UserEntity with user name : " + username + " not found !");
        } else {
            UserEntity userEntity = opt.get();
            return new User(
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getRoles().
                            stream().
                            map(role -> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toSet()));
        }
    }

    public Optional<UserEntity> getUserEntityByEmail(String email) {
        Optional<UserEntity> opt = userRepository.findUserByEmail(email);
        return opt;
    }


    public UserDto updateUser(UserDto userDto){
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
                .accountUpdated(ue2.getAccountUpdated()).build();
    }


    public UserDto saveUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        UserEntity userEntity = UserEntity
                .builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(encodedPassword)
                .email(userDto.getUsername())
                .roles(userDto.getRoles()).build();
        userEntity = userRepository.save(userEntity);

        return UserDto.builder()
                .id(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getEmail())
                .accountCreated(userEntity.getAccountCreated())
                .accountUpdated(userEntity.getAccountUpdated()).build();
    }
}
