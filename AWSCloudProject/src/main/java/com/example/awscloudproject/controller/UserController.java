package com.example.awscloudproject.controller;

import com.example.awscloudproject.dto.UserDto;
import com.example.awscloudproject.exception.ImproperInputException;
import com.example.awscloudproject.exception.UserDoesNotExistException;
import com.example.awscloudproject.service.UserDetailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
public class UserController {

  private final UserDetailService userDetailService;

  /**
   * saves user when a request is sent with new emailId Email, password , firstName and lastName
   * fields must not be null If data is not proper, 400 Bad Request should be sent
   *
   * @param user
   * @return user information
   * @throws if data is not proper, 400 Bad Request should be sent
   */
  @PostMapping("/saveUser")
  public UserDto saveUser(@Valid @RequestBody(required = true) UserDto user) {
    return userDetailService.saveUser(user);
  }

  /**
   * returns the information of current user
   *
   * @param
   * @return user information
   * @throws if data is not proper or data is requested for other user, 400 Bad Request should be
   *     sent
   */
  @GetMapping("/self")
  public UserDto getSelf() throws UserDoesNotExistException {
    String userNameFromMDC = userDetailService.getEmailFromMDC();
    return userDetailService.getUserEntityByEmail(userNameFromMDC);
  }

  /**
   * updating password, firstname or lastname for self
   *
   * @param
   * @return confirmation message
   * @throws 400 bad request if user is updating someone else's data or updating anything other than
   *     First Name, Last Name, or Password
   */
  @PutMapping("/self")
  public UserDto updateUser(@Valid @RequestBody(required = true) UserDto userDto) {
    String userNameFromMDC = userDetailService.getEmailFromMDC();

    if (!userNameFromMDC.equals(userDto.getUsername())) {
      throw new ImproperInputException("Provide correct userName");
    }
    UserDto ud1 = userDetailService.updateUser(userDto);
    return ud1;
  }
}
