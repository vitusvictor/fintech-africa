package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.decagon.fintechpaymentapisqd11b.request.FlwAccountRequest;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.response.FlwAccountResponse;
import com.decagon.fintechpaymentapisqd11b.response.OtherBankTransferResponse;

import java.util.List;

public interface TransferService {
    BaseResponse<OtherBankTransferResponse> initiateOtherBankTransfer(TransferRequest transferRequest);
    List<FlwBank> getBanks();
    BaseResponse<FlwAccountResponse> resolveAccount(FlwAccountRequest flwAccountRequest);
}
