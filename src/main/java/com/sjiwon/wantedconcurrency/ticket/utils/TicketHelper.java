package com.sjiwon.wantedconcurrency.ticket.utils;

import com.sjiwon.wantedconcurrency.ticket.domain.Ticket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketHelper {
    public static void logging(Ticket ticket) {
        log.info(
                "-> Thread [{}] Remain Ticket = {}",
                Thread.currentThread().getName(),
                ticket.getAmount()
        );
    }
}
