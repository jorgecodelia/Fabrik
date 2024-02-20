package com.fabrik.api.common.exceptions;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class AccountNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = -7618845503560119894L;

  public AccountNotFoundException(String message) {
    super(message);
  }
}
