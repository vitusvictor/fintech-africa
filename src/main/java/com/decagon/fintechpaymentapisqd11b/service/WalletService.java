package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.AccountFundDTO;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.math.BigDecimal;

public interface WalletService {

    Wallet createWallet(Users walletRequestDetails) throws JSONException;
    WalletDto viewWalletDetails();

    BaseResponse<String> fundWallet(AccountFundDTO amount);
}
