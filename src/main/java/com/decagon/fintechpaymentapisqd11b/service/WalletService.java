package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.request.FundAccountRequest;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.math.BigDecimal;

public interface WalletService {

    Wallet createWallet(Users walletRequestDetails) throws JSONException;
    WalletDto viewWalletDetails();
    void fundWallet(FundAccountRequest fundAccountRequest);
//    boolean confirmStatus(String status);
//    boolean confirmAmount(Integer amount);
//    boolean confirmTransactionRef(String transactionRef);
//    boolean confirmCurrency(String currency);
}
