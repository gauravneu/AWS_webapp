package com.example.awscloudproject.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class ImproperInputException extends RuntimeException {
  private String message;

  public ImproperInputException(String message) {
    super(message);
    this.message = message;
  }
}
