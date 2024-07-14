package org.soukaruu.transactionfer.services;

import org.soukaruu.transactionfer.entity.Subscriber;
import org.soukaruu.transactionfer.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository repository;

    public List<Subscriber> searchSubscribers(String name) {
        return repository.findByNameContaining(name);
    }

    public Subscriber getSubscriber(String clientId, String product) {
        return repository.findByClientIdAndProduct(clientId, product);
    }

    public Subscriber saveSubscriber(Subscriber subscriber) {
        return repository.save(subscriber);
    }
}
