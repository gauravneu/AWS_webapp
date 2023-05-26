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

    public Optional<UserEntity> getUserEntityByEmail(String email){
        Optional<UserEntity> opt = userRepository.findUserByEmail(email);
        return opt;
    }

    public String saveUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        UserEntity userEntity = UserEntity
                .builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(encodedPassword)
                .email(userDto.getEmail())
                .roles(userDto.getRoles()).build();
        userEntity = userRepository.save(userEntity);

        return userEntity.getUserId();
    }
}
