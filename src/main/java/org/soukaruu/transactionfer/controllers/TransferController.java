package org.soukaruu.transactionfer.controllers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.soukaruu.transactionfer.dtos.TransferRequest;
import org.soukaruu.transactionfer.dtos.TransferResponse;
import org.soukaruu.transactionfer.entity.Transfer;
import org.soukaruu.transactionfer.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferController {
    @Autowired
    private TransferService service;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferFunds(@RequestBody TransferRequest request) {
        try {
            Transfer transfer = service.transferFunds(request.getSourceClientId(), request.getDestinationClientId(), request.getAmount());
            return ResponseEntity.ok(new TransferResponse("Transfer successful", transfer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TransferResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/transfers/{date}")
    public ResponseEntity<InputStreamResource> getTransfersForDay(@PathVariable String date) {
        List<Transfer> transfers = service.getTransfersForDay(LocalDate.parse(date));
        ByteArrayInputStream byteArrayInputStream;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Date du transfert", "ID Client source", "ID Abonné Source", "Montant du transfert", "ID Client destination", "ID Abonné destination"))) {
            for (Transfer transfer : transfers) {
                csvPrinter.printRecord(transfer.getTransferDate(), transfer.getSourceClientId(), transfer.getSourceSubscriberId(), transfer.getAmount(), transfer.getDestinationClientId(), transfer.getDestinationSubscriberId());
            }
            csvPrinter.flush();
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transfers_" + date + ".csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
