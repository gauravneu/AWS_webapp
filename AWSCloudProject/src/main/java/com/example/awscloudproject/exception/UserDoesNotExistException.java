package com.example.awscloudproject.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDoesNotExistException extends Exception {
  private final String message;
}
