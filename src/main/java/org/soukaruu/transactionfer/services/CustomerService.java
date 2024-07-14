package org.soukaruu.transactionfer.services;

import org.soukaruu.transactionfer.dtos.CustomerSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    //private final RestTemplate restTemplate;
    private final WebClient webClient;
    @Value("${EXTEND_API_HOST}")
    private String host;
    @Value("${EXTEND_API_URL}")
    private String url;

    //private final WebClient webClient;

    public CustomerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(host).build();
    }

    public Mono<CustomerSearchResponse[]> searchCustomers(String keywords) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url+keywords)
                        //.queryParam("keywords", keywords)
                        .build())
                .headers(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .retrieve()
                .bodyToMono(CustomerSearchResponse[].class)
                .onErrorResume(throwable -> {
                    // Log or handle the error as needed
                    System.err.println("Error in WebClient request: " + throwable.getMessage());
                    return Mono.empty(); // Return empty result or handle error gracefully
                });
    }
}
