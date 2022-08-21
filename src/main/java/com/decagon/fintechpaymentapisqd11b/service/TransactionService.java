package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.decagon.fintechpaymentapisqd11b.request.FlwAccountRequest;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.request.VerifyTransferRequest;
import com.decagon.fintechpaymentapisqd11b.response.FlwAccountResponse;

import java.util.List;

public interface TransactionService {
    void initiateOtherBankTransfer(TransferRequest transferRequest);
    List<FlwBank> getBanks();
    FlwAccountResponse resolveAccount(FlwAccountRequest flwAccountRequest);
    void verifyTransfer(VerifyTransferRequest verifyTransferRequest);
}
