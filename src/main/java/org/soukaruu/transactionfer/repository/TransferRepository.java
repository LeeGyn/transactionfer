package org.soukaruu.transactionfer.repository;

import org.soukaruu.transactionfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllByTransferDateBetween(LocalDate startDate, LocalDate endDate);
    List<Transfer> findAllByTransferDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
