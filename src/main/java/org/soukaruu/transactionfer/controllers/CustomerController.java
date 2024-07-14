package org.soukaruu.transactionfer.controllers;

import org.soukaruu.transactionfer.dtos.CustomerSearchResponse;
import org.soukaruu.transactionfer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/search")
    public Mono<CustomerSearchResponse[]> searchCustomers(@RequestParam String keywords) {
        return customerService.searchCustomers(keywords);
    }
}
