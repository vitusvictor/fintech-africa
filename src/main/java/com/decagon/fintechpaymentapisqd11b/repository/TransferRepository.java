package com.decagon.fintechpaymentapisqd11b.repository;

import com.decagon.fintechpaymentapisqd11b.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transaction, Long> {

    Transaction findTransfersByFlwRef(Long flwRef);
}
