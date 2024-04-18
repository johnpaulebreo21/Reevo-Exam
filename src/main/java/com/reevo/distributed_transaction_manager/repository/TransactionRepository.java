package com.reevo.distributed_transaction_manager.repository;

import com.reevo.distributed_transaction_manager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction findByTransactionId(String transactionId);
}
