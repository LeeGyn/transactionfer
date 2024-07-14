package org.soukaruu.transactionfer.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CustomerSearchResponse {
    private List<Customer> customers;
    @Data
    public static class Customer {
        private String accountNumber;
        private String fullName;
        private String subscriptionNumber;
        private String badgeNumber;
        private String phoneNumber;
    }
}
