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
      handleDataAccessException(apiResponse, daEx);
    } catch (Exception ex) {
      handleGeneralException(apiResponse, ex);
    }

    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }


  private static <T> void handleDataAccessException(ApiResponse<T> apiResponse, DataAccessException daEx) {
    String error = "Error accessing data";
    apiResponse.addError(error);
    apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    log.error("{}: {}", error, daEx.toString());
  }

  private static <T> void handleGeneralException(ApiResponse<T> apiResponse, Exception ex) {
    String error = "Internal server error";
    apiResponse.addError(error);
    apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    log.error("{}: {}", error, ex.toString());
  }


}
