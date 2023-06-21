package com.sjiwon.wantedconcurrency.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ExceptionResponse {
    private int status;
    private String message;

    private ExceptionResponse(ExceptionCode code) {
        this.status = code.getStatus().value();
        this.message = code.getMessage();
    }

    public static ExceptionResponse from(ExceptionCode errorCode) {
        return new ExceptionResponse(errorCode);
    }

    public static ExceptionResponse of(ExceptionCode errorCode, String message) {
        return new ExceptionResponse(errorCode.getStatus().value(), message);
    }
}
