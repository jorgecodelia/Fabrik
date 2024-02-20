package com.fabrik.api.common.util;

import com.fabrik.api.common.exceptions.AccountNotFoundException;
import com.fabrik.api.common.exceptions.InvalidAccountException;
import com.fabrik.api.common.exceptions.ServiceException;
import com.fabrik.api.domain.EmptyDataResponse;
import java.net.ConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<EmptyDataResponse> handleException(ServiceException exception) {
    return getEmptyDataResponseResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<EmptyDataResponse> handleException(Exception exception) {
    LOGGER.error(exception.getMessage(), exception);
    return getEmptyDataResponseResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConnectException.class)
  public ResponseEntity<EmptyDataResponse> handleException(InvalidAccountException exception) {
    LOGGER.error(exception.getMessage(), exception);
    return getEmptyDataResponseResponseEntity(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<EmptyDataResponse> handleException(
      HttpMessageNotReadableException exception) {
    LOGGER.error(exception.getMessage(), exception);
    return getEmptyDataResponseResponseEntity(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<EmptyDataResponse> handleException(NullPointerException exception) {
    LOGGER.error(exception.getMessage(), exception);
    return getEmptyDataResponseResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<EmptyDataResponse> getEmptyDataResponseResponseEntity(
      Exception exception, HttpStatus httpStatus) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new EmptyDataResponse(exception.getMessage(), httpStatus.toString()));
  }
}
