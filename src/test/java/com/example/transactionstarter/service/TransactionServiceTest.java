//package com.example.transactionstarter.service;
package com.example.transactionstarter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.repository.TransactionRepository;
public class TransactionServiceTest {
	//test 1  Valid transaction
	@Test
	void validTransactionShouldBeSaved() {

	    TransactionRepository repository = mock(TransactionRepository.class);//We don't want to use the real H2 database for this service unit test.

	    TransactionService service = new TransactionService(repository);//fake repo

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN001");
	    transaction.setCustomerId("CUST001");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    when(repository.save(transaction)).thenReturn(transaction);

	    assertDoesNotThrow(() -> service.saveTransaction(transaction));//"I expect this valid transaction to complete without throwing an exception."

	    verify(repository).save(transaction);//"I expect the repository's save() method to have been called.
	}
	
	//test 2 Amount = 0 
	@Test
	void transactionWithZeroAmountShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN002");
	    transaction.setCustomerId("CUST002");
	    transaction.setAmount(BigDecimal.ZERO);
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Amount must be greater than zero",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 3 negeitive amount
	@Test
	void transactionWithNegativeAmountShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN003");
	    transaction.setCustomerId("CUST003");
	    transaction.setAmount(new BigDecimal("-100.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Amount must be greater than zero",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 4 missing Transaction ID
	@Test
	void transactionWithoutTransactionIdShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("");
	    transaction.setCustomerId("CUST004");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Transaction ID is required",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 5 Missing Customer ID
	@Test
	void transactionWithoutCustomerIdShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN005");
	    transaction.setCustomerId("");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Customer ID is required",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 6 Missing Currency
	@Test
	void transactionWithoutCurrencyShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN006");
	    transaction.setCustomerId("CUST006");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Currency is required",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 7 Missing Transaction Type
	@Test
	void transactionWithoutTransactionTypeShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN007");
	    transaction.setCustomerId("CUST007");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("");
	    transaction.setTransactionStatus("SUCCESS");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Transaction type is required",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 8Missing Transaction Status
	@Test
	void transactionWithoutTransactionStatusShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN008");
	    transaction.setCustomerId("CUST008");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("");

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Transaction status is required",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 9 Null Transaction
	@Test
	void nullTransactionShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(null)
	    );

	    org.junit.jupiter.api.Assertions.assertEquals(
	            "Transaction cannot be null",
	            exception.getMessage()
	    );

	    verify(repository, never()).save(any(Transaction.class));
	}
	//test 10 get transcation Get an existing transaction
	@Test
	void getExistingTransactionShouldReturnTransaction() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN010");
	    transaction.setCustomerId("CUST010");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    when(repository.findById("TXN010"))
	            .thenReturn(java.util.Optional.of(transaction));

	    Transaction result = service.getTransaction("TXN010");

	    assertEquals("TXN010", result.getTransactionId());
	    assertEquals("CUST010", result.getCustomerId());
	    assertEquals(new BigDecimal("500.00"), result.getAmount());

	    verify(repository).findById("TXN010");
	}
	//Test 11 — Transaction not found
	@Test
	void getNonExistingTransactionShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    when(repository.findById("TXN999"))
	            .thenReturn(java.util.Optional.empty());

	    IllegalArgumentException exception = assertThrows(
	            IllegalArgumentException.class,
	            () -> service.getTransaction("TXN999")
	    );

	    assertEquals(
	            "Transaction not found",
	            exception.getMessage()
	    );

	    verify(repository).findById("TXN999");
	}
	//Test 12 — Update an existing transaction status
	@Test
	void updateTransactionStatusShouldUpdateAndSaveTransaction() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN012");
	    transaction.setCustomerId("CUST012");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    when(repository.findById("TXN012"))
	            .thenReturn(java.util.Optional.of(transaction));

	    when(repository.save(transaction))
	            .thenReturn(transaction);

	    Transaction result = service.updateTransactionStatus("TXN012", "FAILED");

	    assertEquals("FAILED", result.getTransactionStatus());

	    verify(repository).findById("TXN012");
	    verify(repository).save(transaction);
	}
	//Test 13 — Update status for a transaction that doesn't exist
	@Test
	void updateNonExistingTransactionShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    when(repository.findById("TXN999"))
	            .thenReturn(java.util.Optional.empty());

	    IllegalArgumentException exception = assertThrows(
	            IllegalArgumentException.class,
	            () -> service.updateTransactionStatus("TXN999", "FAILED")
	    );

	    assertEquals(
	            "Transaction not found",
	            exception.getMessage()
	    );

	    verify(repository).findById("TXN999");
	    verify(repository, never()).save(any(Transaction.class));
	}
	//Test 14 — Get transactions for a customer
	@Test
	void getTransactionsByCustomerShouldReturnTransactions() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction1 = new Transaction();
	    transaction1.setTransactionId("TXN014");
	    transaction1.setCustomerId("CUST014");
	    transaction1.setAmount(new BigDecimal("500.00"));
	    transaction1.setCurrency("INR");
	    transaction1.setTransactionType("PAYMENT");
	    transaction1.setTransactionStatus("SUCCESS");

	    Transaction transaction2 = new Transaction();
	    transaction2.setTransactionId("TXN015");
	    transaction2.setCustomerId("CUST014");
	    transaction2.setAmount(new BigDecimal("1000.00"));
	    transaction2.setCurrency("INR");
	    transaction2.setTransactionType("PAYMENT");
	    transaction2.setTransactionStatus("SUCCESS");

	    when(repository.findByCustomerId("CUST014"))
	            .thenReturn(java.util.List.of(transaction1, transaction2));

	    java.util.List<Transaction> result =
	            service.getTransactionsByCustomer("CUST014");

	    assertEquals(2, result.size());
	    assertEquals("TXN014", result.get(0).getTransactionId());
	    assertEquals("TXN015", result.get(1).getTransactionId());

	    verify(repository).findByCustomerId("CUST014");
	}
	//Test 15 — Blank Customer ID
	@Test
	void getTransactionsWithBlankCustomerIdShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    IllegalArgumentException exception = assertThrows(
	            IllegalArgumentException.class,
	            () -> service.getTransactionsByCustomer("")
	    );

	    assertEquals(
	            "Customer ID is required",
	            exception.getMessage()
	    );

	    verify(repository, never()).findByCustomerId(anyString());
	}
	//duplicate transcation rejection if already exist 
	@Test
	void duplicateTransactionIdShouldBeRejected() {

	    TransactionRepository repository = mock(TransactionRepository.class);

	    TransactionService service = new TransactionService(repository);

	    Transaction transaction = new Transaction();

	    transaction.setTransactionId("TXN016");
	    transaction.setCustomerId("CUST016");
	    transaction.setAmount(new BigDecimal("500.00"));
	    transaction.setCurrency("INR");
	    transaction.setTransactionType("PAYMENT");
	    transaction.setTransactionStatus("SUCCESS");

	    when(repository.existsById("TXN016"))
	            .thenReturn(true);

	    IllegalArgumentException exception = assertThrows(
	            IllegalArgumentException.class,
	            () -> service.saveTransaction(transaction)
	    );

	    assertEquals(
	            "Transaction ID already exists",
	            exception.getMessage()
	    );

	    verify(repository).existsById("TXN016");
	    verify(repository, never()).save(any(Transaction.class));
	}
}
