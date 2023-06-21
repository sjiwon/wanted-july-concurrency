package com.sjiwon.wantedconcurrency.ticket.service;

import com.sjiwon.wantedconcurrency.ticket.domain.Ticket;
import com.sjiwon.wantedconcurrency.ticket.domain.TicketRepository;
import com.sjiwon.wantedconcurrency.ticket.utils.TicketHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceWithSynchronized {
    private final TicketRepository ticketRepository;

    public synchronized void buy(String ticketName) {
        // 1. get Ticket
        Ticket ticket = ticketRepository.findByName(ticketName);

        // 2. buy
        ticket.buyTicket();

        // 3. apply
        ticketRepository.saveAndFlush(ticket);

        // 4. logging remain tickets
        TicketHelper.logging(ticket);
    }
}
