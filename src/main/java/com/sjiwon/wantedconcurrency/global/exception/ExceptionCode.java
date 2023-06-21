package com.sjiwon.wantedconcurrency.global.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    HttpStatus getStatus();
    String getMessage();
}
