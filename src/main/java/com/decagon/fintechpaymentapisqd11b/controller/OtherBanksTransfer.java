package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.decagon.fintechpaymentapisqd11b.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
