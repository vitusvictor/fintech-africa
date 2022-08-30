package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.dto.ResolveLocalDTO;
import com.decagon.fintechpaymentapisqd11b.entities.Transaction;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.service.LocalTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController

public class LocalTransferController {

    private final LocalTransferService localTransferService;

    @PostMapping("/transfer/local")
    public String localTransfer(@RequestBody TransferRequest transferRequest){
         localTransferService.makeLocalTransfer(transferRequest);
         return "Transfer successful";
    }

    @PostMapping("/transfer/resolve-local-account")
    public BaseResponse<?> resolveLocalAccount(@RequestBody ResolveLocalDTO accountNumber){
        return localTransferService.resolveLocalAccount(accountNumber);
    }
}
