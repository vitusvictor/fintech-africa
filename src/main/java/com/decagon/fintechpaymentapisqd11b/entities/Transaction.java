package com.decagon.fintechpaymentapisqd11b.entities;

import com.decagon.fintechpaymentapisqd11b.enums.TransactionType;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Transaction extends BaseClass{
    @NotNull
    @Column(length = 11)
    private String senderAccountNumber;

    private String senderFullName;

    private String senderBankName;

    @NotNull
    private String destinationAccountNumber;

    @NotNull
    private String destinationFullName;

    @NotNull
    private String destinationBank;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String narration;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UsersStatus userStatus;

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

