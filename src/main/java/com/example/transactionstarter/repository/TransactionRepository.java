package com.example.transactionstarter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transactionstarter.transaction.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
	//This repository manages Transaction objects, and the ID of a Transaction is a String."
	List<Transaction> findByCustomerId(String customerId);
}


