package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.decagon.fintechpaymentapisqd11b.request.VerifyTransferRequest;
import com.decagon.fintechpaymentapisqd11b.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/api/v1/transfer")
public class OtherBanksTransfer {
    private TransferService transferService;

    @GetMapping("/banks")
    public List<FlwBank> getBanks() {
        return transferService.getBanks();

    }

    @PostMapping("/verify-transfer")
    public void verify(@RequestBody VerifyTransferRequest verifyTransferRequest){

    }
}
