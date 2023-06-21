package com.sjiwon.wantedconcurrency.ticket.service;

import com.sjiwon.wantedconcurrency.ticket.domain.Ticket;
import com.sjiwon.wantedconcurrency.ticket.domain.TicketRepository;
import com.sjiwon.wantedconcurrency.ticket.utils.TicketHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketServiceWithPessimisticLock {
    private final TicketRepository ticketRepository;

    @Transactional
    public void buy(String ticketName) {
        // 1. get Ticket
        Ticket ticket = ticketRepository.findByNameWithPessimisticLock(ticketName);

        // 2. buy
        ticket.buyTicket();

        // 3. logging remain tickets
        TicketHelper.logging(ticket);
    }
}
