package com.example.awscloudproject.exception;

public class UserDoesNotExistException extends Exception {
  private String message;

  public UserDoesNotExistException(String message) {
    super(message);
    this.message = message;
  }
}
