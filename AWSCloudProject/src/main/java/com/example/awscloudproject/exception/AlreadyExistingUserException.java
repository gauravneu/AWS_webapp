package com.example.awscloudproject.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
@AllArgsConstructor
public class AlreadyExistingUserException extends RuntimeException {
  private String message;
}
