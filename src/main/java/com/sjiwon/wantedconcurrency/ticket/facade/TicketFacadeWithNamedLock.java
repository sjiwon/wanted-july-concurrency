package com.sjiwon.wantedconcurrency.ticket.facade;

import com.sjiwon.wantedconcurrency.ticket.domain.TicketRepository;
import com.sjiwon.wantedconcurrency.ticket.service.TicketServiceWithNamedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketFacadeWithNamedLock {
    private final TicketRepository ticketRepository;
    private final TicketServiceWithNamedLock ticketServiceWithNamedLock;

    public void buy(String ticketName) {
        ticketRepository.getLock(ticketName);

        try {
            ticketServiceWithNamedLock.buy(ticketName);
        } finally {
            ticketRepository.releaseLock(ticketName);
        }
    }
}
