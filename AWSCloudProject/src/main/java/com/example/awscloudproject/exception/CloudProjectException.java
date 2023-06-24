package com.example.awscloudproject.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CloudProjectException extends Exception {
    private String message;
    private Exception ex;
}
