package com.sjiwon.wantedconcurrency.global.exception;

import lombok.Getter;

@Getter
public class WantedConcurrencyException extends RuntimeException {
    private final ExceptionCode code;

    public WantedConcurrencyException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public static WantedConcurrencyException type(ExceptionCode code) {
        return new WantedConcurrencyException(code);
    }
}
