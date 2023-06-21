package com.sjiwon.wantedconcurrency.ticket.facade;

import com.sjiwon.wantedconcurrency.ticket.service.TicketServiceWithOptimisticLock;
import com.sjiwon.wantedconcurrency.ticket.utils.TicketHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketFacadeWithOptimisticLock {
    private final TicketServiceWithOptimisticLock ticketServiceWithOptimisticLock;

    public void buy(String ticketName) throws InterruptedException {
        while (true) {
            try {
                ticketServiceWithOptimisticLock.buy(ticketName);
                break;
            } catch (Exception e) {
                TicketHelper.loggingOptimisticException();
                Thread.sleep(50); // 50ms 대기 후 Retry
            }
        }
    }
}
