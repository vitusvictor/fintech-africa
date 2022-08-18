package com.decagon.fintechpaymentapisqd11b.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferRequest {
//    @NotNull(message = "User id is required")
//    private Long userId; // user doesn't provide their id
    @NotNull(message = "Account number is required")
    private String accountNumber;
    @NotNull(message = "Account name is required")
    private String accountName;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
//    @NotNull(message = "Bank code is required") // user doesn't provide bank code
//    private String bankCode;
    @NotNull(message = "Pin is required")
    private String pin;
    @NotNull(message = "Narration is required")
    private String narration;
    private String status;

}
