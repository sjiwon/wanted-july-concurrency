package com.sjiwon.wantedconcurrency.ticket.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByName(String ticketName);
}
