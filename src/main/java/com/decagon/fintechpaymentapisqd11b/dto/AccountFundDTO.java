package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class AccountFundDTO {
    private BigDecimal amount;
}
