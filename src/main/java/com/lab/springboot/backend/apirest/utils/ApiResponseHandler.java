package com.lab.springboot.backend.apirest.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

@Slf4j
public class ApiResponseHandler {

  @FunctionalInterface
  private interface ApiResponseHandlerInterface<T> {
    ApiResponse<T> excecuteLogic();
  }

  public static <T> ApiResponse<T> handleResponse(ApiResponseHandlerInterface<T> handler) {
    ApiResponse<T> apiResponse = new ApiResponse<>();
    try {
      ApiResponse<T> tempResponse = handler.excecuteLogic();
      if (tempResponse != null) {
        apiResponse = tempResponse;
      }
    } catch (DataAccessException daEx) {
      String error = "Error to access the data";
      apiResponse.addError(error);
      apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      log.error("{}: {}", error, daEx.toString());
    } catch (Exception ex) {
      String error = "System Internal Error";
      apiResponse.addError(error);
      apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      log.error("{}: {}", error, ex.toString());
    }

    return apiResponse;
  }

}
