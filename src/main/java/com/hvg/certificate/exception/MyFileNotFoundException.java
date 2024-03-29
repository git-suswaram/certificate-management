package com.hvg.certificate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyFileNotFoundException extends RuntimeException {

  public MyFileNotFoundException(String message) {
    super(message);
  }

  public MyFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}