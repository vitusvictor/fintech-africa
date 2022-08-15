package com.decagon.fintechpaymentapisqd11b.request;

import javax.validation.constraints.NotNull;

public class TransferRequest {

    @NotNull(message = "account number is required")
    private String accountNumber;

    @NotNull(message = "account name is required")
    private String accountName;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "narration is required")
    private String narration;

    @NotNull(message = "Pin is required")
    private String pin;
}
