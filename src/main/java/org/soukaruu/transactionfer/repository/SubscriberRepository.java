package org.soukaruu.transactionfer.repository;

import org.soukaruu.transactionfer.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    List<Subscriber> findByNameContaining(String name);
    Subscriber findByClientIdAndProduct(String clientId, String product);
}
