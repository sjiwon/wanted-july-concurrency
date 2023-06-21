package com.sjiwon.wantedconcurrency.ticket.facade;

import com.sjiwon.wantedconcurrency.ticket.service.TicketServiceWithRedisRedissonClientLock;
import com.sjiwon.wantedconcurrency.ticket.utils.TicketHelper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TicketFacadeWithRedisRedissonClientLock {
    private final RedissonClient redissonClient;
    private final TicketServiceWithRedisRedissonClientLock ticketServiceWithRedisRedissonClientLock;

    public void buy(String ticketName) throws InterruptedException {
        RLock lock = redissonClient.getLock(ticketName);

        while (!lock.tryLock(5, 1, TimeUnit.SECONDS)) {
            TicketHelper.loggingRedisDistributedLockByRedissonClient();
        }

        try {
            ticketServiceWithRedisRedissonClientLock.buy(ticketName);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
