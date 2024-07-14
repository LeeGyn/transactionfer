package org.soukaruu.transactionfer.dtos;

import lombok.Data;

@Data
public class TransferRequest {
    private String sourceClientId;
    private String destinationClientId;
    private Double amount;
}
