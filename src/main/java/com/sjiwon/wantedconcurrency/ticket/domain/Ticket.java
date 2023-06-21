package com.sjiwon.wantedconcurrency.ticket.domain;

import com.sjiwon.wantedconcurrency.global.exception.WantedConcurrencyException;
import com.sjiwon.wantedconcurrency.ticket.exception.TicketException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int amount;

    @Version
    private Long version;

    public Ticket(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void buyTicket() {
        validateTicketRemains();
        amount--;
    }

    private void validateTicketRemains() {
        if (amount == 0) {
            throw WantedConcurrencyException.type(TicketException.TICKET_NOT_ENOUGH);
        }
    }
}
