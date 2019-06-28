package com.hvg.certificate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCertFileException extends RuntimeException {

  public InvalidCertFileException(String message) {
    super(message);
  }

  public InvalidCertFileException(String message, Throwable cause) {
    super(message, cause);
  }
}