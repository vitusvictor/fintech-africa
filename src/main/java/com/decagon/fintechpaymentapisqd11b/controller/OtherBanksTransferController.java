package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.decagon.fintechpaymentapisqd11b.request.FlwAccountRequest;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.request.VerifyTransferRequest;
import com.decagon.fintechpaymentapisqd11b.response.FlwAccountResponse;
import com.decagon.fintechpaymentapisqd11b.service.TransferService;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.OtherBanksTransferImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/api/v1/transfer")
public class OtherBanksTransferController {
    private final TransferService transferService;

    private final OtherBanksTransferImpl otherBanksTransfer;

    @GetMapping("/banks")
    public List<FlwBank> getBanks() {
        return transferService.getBanks();
    }

    @PostMapping("/otherbank-account-query")
    public FlwAccountResponse resolveAccount(@RequestBody FlwAccountRequest flwAccountRequest) {
        return transferService.resolveAccount(flwAccountRequest);
    }

    @PostMapping("/other-bank ")
    public void makeTransfer(@RequestBody TransferRequest transferRequest) {
        transferService.initiateOtherBankTransfer(transferRequest);
    }

    @PostMapping("/verify-transfer")
    public ResponseEntity<String> verify(@RequestBody VerifyTransferRequest verifyTransferRequest){
        otherBanksTransfer.verifyTransfer(verifyTransferRequest);
    return ResponseEntity.ok("status updated");
    }
}
