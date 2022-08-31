package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.AccountFundDTO;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.request.FundAccountRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;


public interface WalletService {

    Wallet createWallet(Users walletRequestDetails) throws JSONException;
    WalletDto viewWalletDetails();
    void fundWallet(FundAccountRequest fundAccountRequest);

    BaseResponse<String> fundWallet(AccountFundDTO amount);
}
