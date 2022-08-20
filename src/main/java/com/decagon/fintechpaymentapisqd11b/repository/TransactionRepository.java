package com.decagon.fintechpaymentapisqd11b.repository;

import com.decagon.fintechpaymentapisqd11b.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllBySenderAccountNumberOrDestinationAccountNumber
            (String sender, String recipient, Pageable pageable);

}

