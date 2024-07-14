package org.soukaruu.transactionfer.services;

import org.soukaruu.transactionfer.entity.Subscriber;
import org.soukaruu.transactionfer.entity.Transfer;
import org.soukaruu.transactionfer.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {
    @Autowired
    private TransferRepository repository;

    @Autowired
    private SubscriberService subscriberService;

    @Transactional
    public Transfer transferFunds(String sourceClientId, String destinationClientId, Double amount) throws Exception {
        Subscriber source = subscriberService.getSubscriber(sourceClientId, "PRODUCT_TYPE");
        Subscriber destination = subscriberService.getSubscriber(destinationClientId, "PRODUCT_TYPE");

        if (source == null || destination == null || !source.getProduct().equals(destination.getProduct())) {
            throw new Exception("Invalid transfer");
        }

        if (source.getBalance() - amount < 5000) {
            throw new Exception("Insufficient balance");
        }

        source.setBalance(source.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);

        subscriberService.saveSubscriber(source);
        subscriberService.saveSubscriber(destination);

        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDateTime.now());
        transfer.setAmount(amount);
        transfer.setSourceClientId(sourceClientId);
        transfer.setSourceSubscriberId(String.valueOf(source.getId()));
        transfer.setDestinationClientId(destinationClientId);
        transfer.setDestinationSubscriberId(String.valueOf(destination.getId()));

        return repository.save(transfer);
    }

    public List<Transfer> getTransfersForDay(LocalDate date) {
        return repository.findAllByTransferDateBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
}
