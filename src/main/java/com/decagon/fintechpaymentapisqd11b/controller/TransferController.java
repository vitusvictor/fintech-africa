package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> localTransfer(@RequestBody TransferRequest transferRequest){
        return new ResponseEntity<>(transactionService.localTransfer(transferRequest), HttpStatus.OK);
    }
}
