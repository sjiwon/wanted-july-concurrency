package com.sjiwon.wantedconcurrency.global;

import com.sjiwon.wantedconcurrency.ticket.domain.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConcurrencyTest {
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected TicketRepository ticketRepository;

    @AfterEach
    void clearDatabase() {
        databaseCleaner.cleanUpDatabase();
    }
}
