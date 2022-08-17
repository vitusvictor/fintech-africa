package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;

public interface LocalTransferService {

    String localTransfer(TransferRequest transferRequest);
}
