package com.reevo.distributed_transaction_manager.controller;

import com.reevo.distributed_transaction_manager.model.Transaction;
import com.reevo.distributed_transaction_manager.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/start-transaction")
    public ResponseEntity<String> startTransaction() {
        //create random transactionID
        UUID uuid = UUID.randomUUID();
        String transaction_id = uuid.toString();
        return ResponseEntity.ok(transaction_id);
    }

    @PostMapping("/commit-transaction/{transaction_id}")
    @Transactional
    public ResponseEntity<String> commitTransaction(@PathVariable("transaction_id") String transactionId) {
        // Commit the transaction with the given ID
        Transaction transaction = Transaction.builder().transactionId(transactionId).build();
        transactionRepository.save(transaction);
        return ResponseEntity.ok("Transaction committed: " + transactionId);
    }

    @PostMapping("/rollback-transaction/{transaction_id}")
    public ResponseEntity<String> rollbackTransaction(@PathVariable("transaction_id") String
                                                              transactionId) {
        // Roll back the transaction with the given ID
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction != null) {
            transactionRepository.delete(transaction);
        }
        return ResponseEntity.ok("Transaction rolled back: " + transactionId);
    }
}