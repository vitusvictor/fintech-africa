package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.ResolveLocalDTO;
import com.decagon.fintechpaymentapisqd11b.entities.Transaction;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;

public interface LocalTransferService {

//    String localTransfer(TransferRequest transferRequest);

    BaseResponse<Transaction> makeLocalTransfer(TransferRequest transferRequest);

    BaseResponse<?> resolveLocalAccount(ResolveLocalDTO accountNumber);
}
