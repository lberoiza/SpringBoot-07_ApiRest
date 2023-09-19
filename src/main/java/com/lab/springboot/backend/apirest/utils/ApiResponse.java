package com.lab.springboot.backend.apirest.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiResponse<T> {

  private static final String MESSAGE_SUCCESS = "success";
  private static final String MESSAGE_WARNING = "warning";
  private static final String MESSAGE_ERROR = "error";

  @Getter
  private Map<String, List<String>> messages;

  @Getter
  @JsonProperty("response")
  private T responseData;

  @Getter
  private HttpStatus httpStatus;

  @Getter
  private int httpCode;

  public ApiResponse() {
    this.setHttpStatus(HttpStatus.OK);
    this.initializeMessages();
  }

  private void initializeMessages() {
    this.messages = new HashMap<>();
    this.messages.put(MESSAGE_SUCCESS, new ArrayList<>());
    this.messages.put(MESSAGE_WARNING, new ArrayList<>());
    this.messages.put(MESSAGE_ERROR, new ArrayList<>());
  }

  private void addMessage(String message, String messageTyp) {
    this.messages.get(messageTyp).add(message);
  }

  public void addSuccess(String message) {
    this.addMessage(message, MESSAGE_SUCCESS);
  }

  public void addWarning(String message) {
    this.addMessage(message, MESSAGE_WARNING);
  }

  public void addError(String message) {
    this.addMessage(message, MESSAGE_ERROR);
  }

  public void addResponseData(T responseData) {
    this.responseData = responseData;
  }

  @JsonProperty("isSuccessfully")
  public boolean isSuccessfully() {
    return this.messages.get(MESSAGE_ERROR).isEmpty();
  }

  public boolean hasErrors() {
    return !this.isSuccessfully();
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    this.httpCode = httpStatus.value();
  }
}
