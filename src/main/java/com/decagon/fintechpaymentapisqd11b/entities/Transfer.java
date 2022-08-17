package com.decagon.fintechpaymentapisqd11b.entities;

import com.decagon.fintechpaymentapisqd11b.enums.TransactionType;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Transfer extends BaseClass{
    @NotNull
    @Column(length = 11)
    private String sourceAccount;

    @NotNull
    private String destinationAccountNumber;

    @NotNull
    private String destinationAccountName;

    @NotNull
    private String destinationBank;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String narration;

    @NotNull
    private String clientRef;

    @NotNull
    private Long flwRef;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    private String transferStatus;

}

