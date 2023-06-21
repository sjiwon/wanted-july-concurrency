package com.sjiwon.wantedconcurrency.ticket.exception;

import com.sjiwon.wantedconcurrency.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TicketException implements ExceptionCode {
    TICKET_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "티겟이 매진되었습니다."),
    ;

    public final HttpStatus status;
    public final String message;
}
