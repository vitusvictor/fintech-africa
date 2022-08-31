package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.dto.AccountFundDTO;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.request.FundAccountRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
   private final WalletService walletService;

    @PostMapping("/fund")
    public ResponseEntity<String> fundWallet(@RequestBody FundAccountRequest fundAccountRequest) {
        walletService.fundWallet(fundAccountRequest);
        return ResponseEntity.ok("WebHook received");
    }

    @GetMapping("/viewWalletDetails")
    public ResponseEntity<WalletDto> viewWalletDetails()  {
        return new ResponseEntity<>(walletService.viewWalletDetails(), HttpStatus.OK);
    }

    @PostMapping("/fundLocalWallet")
    public BaseResponse<String> accountFund(@RequestBody AccountFundDTO amount){
        return walletService.fundWallet(amount);
    }

}
