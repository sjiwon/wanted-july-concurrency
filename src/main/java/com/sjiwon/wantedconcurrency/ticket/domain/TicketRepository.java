package com.sjiwon.wantedconcurrency.ticket.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // 1. synchronized
    Ticket findByName(String ticketName);

    // 2. Pessimistic Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.name = :ticketName")
    Ticket findByNameWithPessimisticLock(@Param("ticketName") String ticketName);

    // 3. Optimistic Lock
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT t FROM Ticket t WHERE t.name = :ticketName")
    Ticket findByNameWithOptimisticLock(@Param("ticketName") String ticketName);

    // 4. Named Lock
    @Query(value = "SELECT GET_LOCK(:key, 10)", nativeQuery = true)
    void getLock(@Param("key") String key);

    @Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
    void releaseLock(@Param("key") String key);
}
