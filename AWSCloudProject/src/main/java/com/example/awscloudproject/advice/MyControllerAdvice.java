package com.example.awscloudproject.advice;

import com.example.awscloudproject.exception.AlreadyExistingUserException;
import com.example.awscloudproject.exception.ImproperInputException;
import com.example.awscloudproject.exception.UserDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class MyControllerAdvice {

  /** @throws 400 Bad Request if already existing user is tried to be persisted */
  @ExceptionHandler(AlreadyExistingUserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleAlreadyExistingUserException(AlreadyExistingUserException ex) {
    return "This User Already Exists";
  }

  /** @throws 400 Bad Request if Invalid Input Request is sent to persist user */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    return "Not all input parameters are present";
  }

  /** @throws 400 Bad Request if Invalid Input Request is sent to authenticate user */
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleBadCredentialsException(BadCredentialsException ex) {
    return "Invalid Credentials";
  }

  /** @throws 400 Bad Request if No Request Body is sent to authenticate user */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return "Request Body is Required";
  }

  /** @throws 400 Bad Request if No Request Body is sent to authenticate user */
  @ExceptionHandler(UserDoesNotExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleUserDoesNotExistException(UserDoesNotExistException ex) {
    return "User does not exist";
  }

  /** @throws 400 Bad Request if No Token is provided for protected endpoint */
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return "Incorrect endpoint";
  }

  /** @throws 400 Bad Request if No Token is provided for protected endpoint */
  @ExceptionHandler(InsufficientAuthenticationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleException(InsufficientAuthenticationException ex) {
    return "Insufficient Authentication";
  }

  @ExceptionHandler(ImproperInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleException(ImproperInputException ex) {
    return "Provide correct userName";
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleException(Exception ex) {
    log.info(ex.toString());
    return "User does not exist";
  }
}
