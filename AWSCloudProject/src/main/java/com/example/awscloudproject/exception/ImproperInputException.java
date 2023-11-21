package com.example.awscloudproject.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@Data
@RequiredArgsConstructor
public class ImproperInputException extends RuntimeException {
  private final String message;
}
