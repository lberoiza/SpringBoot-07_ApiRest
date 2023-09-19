package com.lab.springboot.backend.apirest.utils;


import lombok.Getter;
import org.springframework.http.HttpStatus;


public class ApiServiceException extends RuntimeException {

  @Getter
  private HttpStatus httpStatus;

  public ApiServiceException(String message, HttpStatus status) {
    super(message);
    this.httpStatus = status;
  }

  public ApiServiceException(String message) {
    this(message, HttpStatus.BAD_REQUEST);
  }

}
