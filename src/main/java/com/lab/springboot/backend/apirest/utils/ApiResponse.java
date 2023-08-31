package com.lab.springboot.backend.apirest.utils;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiResponse<T> {

  private static String MESSAGE_SUCCESS = "success";
  private static String MESSAGE_WARNING = "warning";
  private static String MESSAGE_ERROR = "error";

  private Map<String, List<String>> messages;
  private T responseData;
  private HttpStatus httpStatus;

  public ApiResponse() {
    this.httpStatus = HttpStatus.OK;
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

  public boolean isSuccessfull() {
    return this.messages.get(MESSAGE_ERROR).isEmpty();
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

}
