package com.sinclair.vpreports.spreadsheetrefresh.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler({ReportException.class})
    ResponseEntity<CustomErrorMessage> handleAuthenticationException(ReportException ex) throws IOException {
        return new ResponseEntity<>(ex.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
