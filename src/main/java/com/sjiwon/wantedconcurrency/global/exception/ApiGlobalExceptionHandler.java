package com.sjiwon.wantedconcurrency.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiGlobalExceptionHandler {
    @ExceptionHandler(WantedConcurrencyException.class)
    public ResponseEntity<ExceptionResponse> catchWantedConcurrentException(WantedConcurrencyException exception) {
        ExceptionCode code = exception.getCode();
        logging(code);

        return ResponseEntity
                .status(code.getStatus())
                .body(ExceptionResponse.from(code));
    }

    private void logging(ExceptionCode code) {
        log.warn("{} | {}", code.getStatus(), code.getMessage());
    }
}
