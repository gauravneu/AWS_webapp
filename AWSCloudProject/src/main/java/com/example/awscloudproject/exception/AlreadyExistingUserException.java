package com.example.awscloudproject.exception;

import lombok.*;

@Builder
@Getter
@Data
@RequiredArgsConstructor
public class AlreadyExistingUserException extends RuntimeException {
  private final String message;
}
