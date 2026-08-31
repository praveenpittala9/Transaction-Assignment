package com.example.transactionstarter.service;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import com.example.transactionstarter.repository.TransactionRepository;
import com.example.transactionstarter.transaction.Transaction;
import java.util.List;

@Service  //bussines logic
public class TransactionService {

    private final TransactionRepository transactionRepository; //repo variable

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;//constructor
    }


private void validateTransaction(Transaction transaction) {

    if (transaction == null) {
        throw new IllegalArgumentException("Transaction cannot be null");
    }

    if (transaction.getTransactionId() == null ||
            transaction.getTransactionId().isBlank()) {
        throw new IllegalArgumentException("Transaction ID is required");
    }

    if (transaction.getCustomerId() == null ||
            transaction.getCustomerId().isBlank()) {
        throw new IllegalArgumentException("Customer ID is required");
    }

    if (transaction.getAmount() == null ||
            transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Amount must be greater than zero");
    }

    if (transaction.getCurrency() == null ||
            transaction.getCurrency().isBlank()) {
        throw new IllegalArgumentException("Currency is required");
    }

    if (transaction.getTransactionType() == null ||
            transaction.getTransactionType().isBlank()) {
        throw new IllegalArgumentException("Transaction type is required");
    }

    if (transaction.getTransactionStatus() == null ||
            transaction.getTransactionStatus().isBlank()) {
        throw new IllegalArgumentException("Transaction status is required");
    }
}
//save transcation operations 
public Transaction saveTransaction(Transaction transaction) {

    validateTransaction(transaction);

    if (transactionRepository.existsById(transaction.getTransactionId())) {
        throw new IllegalArgumentException("Transaction ID already exists");
    }

    return transactionRepository.save(transaction);
}
//Get transaction
public Transaction getTransaction(String transactionId) {

    if (transactionId == null || transactionId.isBlank()) {
        throw new IllegalArgumentException("Transaction ID is required");
    }

    return transactionRepository.findById(transactionId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Transaction not found"));
}
//put transcation update transcation // http://localhost:8080/api/transactions/TXN001/status?status=FAILED
public Transaction updateTransactionStatus(String transactionId, String status) {

    if (transactionId == null || transactionId.isBlank()) {
        throw new IllegalArgumentException("Transaction ID is required");
    }

    if (status == null || status.isBlank()) {
        throw new IllegalArgumentException("Transaction status is required");
    }

    Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Transaction not found"));

    transaction.setTransactionStatus(status);

    return transactionRepository.save(transaction);
}
//get transcation from customers details 
public List<Transaction> getTransactionsByCustomer(String customerId) {

    if (customerId == null || customerId.isBlank()) {
        throw new IllegalArgumentException("Customer ID is required");
    }

    return transactionRepository.findByCustomerId(customerId);
}
}