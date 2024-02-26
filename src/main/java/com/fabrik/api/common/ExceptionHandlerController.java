package com.fabrik.api.common;

import com.fabrik.api.common.domain.ClientResponse;
import com.fabrik.api.common.domain.Error;
import com.fabrik.api.common.exception.BadRequestException;
import com.fabrik.api.common.exception.ForbiddenException;
import com.fabrik.api.common.exception.NoContentException;
import com.fabrik.api.common.exception.ServiceException;
import com.fabrik.api.common.exception.UnauthorizedException;
import com.fabrik.api.common.domain.MainApiResponse;
import com.fabrik.api.common.util.FabrikUtil;
import com.google.gson.Gson;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MainApiResponse> handleException(UnauthorizedException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<MainApiResponse> handleException(ForbiddenException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MainApiResponse> handleException(BadRequestException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<MainApiResponse> handleException(NoContentException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<MainApiResponse> handleException(FeignException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.valueOf(exception.status()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<MainApiResponse> handleException(ServiceException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MainApiResponse> handleException(NullPointerException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MainApiResponse> handleException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        return getEmptyDataResponseResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<MainApiResponse> getEmptyDataResponseResponseEntity(Exception exception, HttpStatus httpStatus) {
        if (exception instanceof FeignException) {
            String msgResponse = exception.getMessage().replaceAll("\\s", "");
            msgResponse = msgResponse.replaceAll("errors", "error");
            Gson gson = new Gson();
            ClientResponse clientResponse = gson.fromJson(FabrikUtil.extractErrorFromResponse(msgResponse), ClientResponse.class);
            Error error = clientResponse.getError().get(0);
            return ResponseEntity.status(httpStatus.value())
                    .body(new MainApiResponse(error.getDescription(), error.getCode(), String.valueOf(httpStatus.value())));
        }
        return ResponseEntity.status(httpStatus.value())
                .body(new MainApiResponse(exception.getMessage(), String.valueOf(httpStatus.value()), httpStatus.getReasonPhrase()));
    }
}
