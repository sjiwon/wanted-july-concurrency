package com.sjiwon.wantedconcurrency.ticket.service;

import com.sjiwon.wantedconcurrency.global.ConcurrencyTest;
import com.sjiwon.wantedconcurrency.ticket.domain.Ticket;
import com.sjiwon.wantedconcurrency.ticket.facade.TicketFacadeWithNamedLock;
import com.sjiwon.wantedconcurrency.ticket.facade.TicketFacadeWithOptimisticLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class TicketConcurrencyTest extends ConcurrencyTest {
    @Autowired
    private TicketServiceWithSynchronized ticketServiceWithSynchronized;

    @Autowired
    private TicketServiceWithPessimisticLock ticketServiceWithPessimisticLock;

    @Autowired
    private TicketFacadeWithOptimisticLock ticketFacadeWithOptimisticLock;

    @Autowired
    private TicketFacadeWithNamedLock ticketFacadeWithNamedLock;

    private static final int THREAD_COUNT = 100;
    private static final String TICKET_NAME = "현대카드 슈퍼콘서트 27 브루노 마스(Bruno Mars)";

    @BeforeEach
    void setUp() {
        ticketRepository.save(new Ticket(TICKET_NAME, 100));
    }

    @Test
    @DisplayName("100명의 사용자가 남은 티켓 100장을 한장씩 구매한다 [With Synchronized]")
    void buyTicketWithSynchronized() throws InterruptedException {
        // given
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // when
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    ticketServiceWithSynchronized.buy(TICKET_NAME);
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        assertThat(getAuctionRecordCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("100명의 사용자가 남은 티켓 100장을 한장씩 구매한다 [With Pessimistic Lock]")
    void buyTicketWithPessimisticLock() throws InterruptedException {
        // given
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // when
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    ticketServiceWithPessimisticLock.buy(TICKET_NAME);
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        assertThat(getAuctionRecordCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("100명의 사용자가 남은 티켓 100장을 한장씩 구매한다 [With Optimistic Lock]")
    void buyTicketWithOptimisticLock() throws InterruptedException {
        // given
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // when
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    ticketFacadeWithOptimisticLock.buy(TICKET_NAME);
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        assertThat(getAuctionRecordCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("100명의 사용자가 남은 티켓 100장을 한장씩 구매한다 [With Named Lock]")
    void buyTicketWithNamedLock() throws InterruptedException {
        // given
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // when
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    ticketFacadeWithNamedLock.buy(TICKET_NAME);
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        assertThat(getAuctionRecordCount()).isEqualTo(0);
    }

    private int getAuctionRecordCount() {
        return em.createQuery(
                        "SELECT t.amount" +
                                " FROM Ticket t" +
                                " WHERE t.name = :ticketName",
                        Integer.class
                )
                .setParameter("ticketName", TICKET_NAME)
                .getSingleResult();
    }
}
