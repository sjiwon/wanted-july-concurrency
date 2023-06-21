package com.sjiwon.wantedconcurrency.ticket.facade;

import com.sjiwon.wantedconcurrency.ticket.infra.redis.RedisLettuceClientLockRepository;
import com.sjiwon.wantedconcurrency.ticket.service.TicketServiceWithRedisLettuceClientLock;
import com.sjiwon.wantedconcurrency.ticket.utils.TicketHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketFacadeWithRedisLettuceClientLock {
    private final RedisLettuceClientLockRepository redisLettuceClientLockRepository;
    private final TicketServiceWithRedisLettuceClientLock ticketServiceWithRedisLettuceClientLock;

    public void buy(String ticketName) throws InterruptedException {
        while (!redisLettuceClientLockRepository.lock(ticketName)) {
            TicketHelper.loggingRedisDistributedLockByLettuceClient();
            Thread.sleep(50); // Spin Lock 부하를 줄이기 위해서 50ms 대기 후 다시 Lock 획득 시도
        }

        try {
            ticketServiceWithRedisLettuceClientLock.buy(ticketName);
        } finally {
            redisLettuceClientLockRepository.unlock(ticketName);
        }
    }
}
