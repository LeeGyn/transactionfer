package org.soukaruu.transactionfer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.soukaruu.transactionfer.entity.Transfer;

@Data
@AllArgsConstructor
public class TransferResponse {
    private String message;
    private Transfer transfer;
}
