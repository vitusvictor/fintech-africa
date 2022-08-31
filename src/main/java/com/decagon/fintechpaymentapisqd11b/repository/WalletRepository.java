package com.decagon.fintechpaymentapisqd11b.repository;

import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByUsers(Users user);

    Wallet findWalletByAccountNumber(String accountNumber);
    Wallet findByTxRef(String txRef);
}
