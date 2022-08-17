package com.decagon.fintechpaymentapisqd11b.repository;

import com.decagon.fintechpaymentapisqd11b.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
