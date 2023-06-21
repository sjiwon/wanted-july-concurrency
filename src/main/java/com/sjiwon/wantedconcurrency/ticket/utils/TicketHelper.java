package com.sjiwon.wantedconcurrency.ticket.utils;

import com.sjiwon.wantedconcurrency.ticket.domain.Ticket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketHelper {
    public static void loggingTicketBuyProcess(Ticket ticket) {
        log.info(
                "-> Thread [{}] Remain Ticket = {}",
                Thread.currentThread().getName(),
                ticket.getAmount()
        );
    }

    public static void loggingOptimisticException() {
        log.info(
                "--> Thread [{}] Optimistic Lock 프로세스 간 version에 의한 재시도",
                Thread.currentThread().getName()
        );
    }
}
