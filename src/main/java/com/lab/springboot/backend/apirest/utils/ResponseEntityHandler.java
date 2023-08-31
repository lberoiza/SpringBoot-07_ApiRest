package com.lab.springboot.backend.apirest.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseEntityHandler {

  @FunctionalInterface
  public interface ApiResponseHandlerInterface<T> {
    ApiResponse<T> executeLogic();
  }

  public static <T> ResponseEntity<ApiResponse<T>> handleApiResponse(ApiResponseHandlerInterface<T> handler) {
    ApiResponse<T> apiResponse = new ApiResponse<>();
    try {
      ApiResponse<T> tempResponse = handler.executeLogic();
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

    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }

}
