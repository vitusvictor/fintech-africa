package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.service.LocalTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping (path = "/local-transfer")
public class LocalTransferController {

    private final LocalTransferService localTransferService;

    @PostMapping("/transfer/local")
    public ResponseEntity<String> localTransfer(@RequestBody TransferRequest transferRequest){
        return new ResponseEntity<>(localTransferService.localTransfer(transferRequest), HttpStatus.OK);
    }
}
