package com.fabrik.api.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccountException extends RuntimeException {

  private static final long serialVersionUID = -7383848562830056926L;

  public InvalidAccountException(String message) {
    super(message);
  }

  public InvalidAccountException(String message, Throwable cause) {
    super(message, cause);
  }
}
