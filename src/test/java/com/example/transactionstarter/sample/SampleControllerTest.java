package com.example.transactionstarter.sample;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.example.transactionstarter.service.TransactionService;
import com.example.transactionstarter.transaction.Transaction;

public class SampleControllerTest {
//test 1
    @Test
    void validTransactionShouldReturnTransaction() {

        TransactionService service = mock(TransactionService.class);

        SampleController controller = new SampleController(service);

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN001");
        transaction.setCustomerId("CUST001");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("SUCCESS");

        when(service.saveTransaction(transaction)).thenReturn(transaction);

        Transaction result = controller.createTransaction(transaction);

        assertEquals("TXN001", result.getTransactionId());
        assertEquals("CUST001", result.getCustomerId());
        assertEquals(new BigDecimal("500.00"), result.getAmount());

        verify(service).saveTransaction(transaction);
    }
    //test 2 automated Controller test for invalid transaction
    @Test
    void invalidTransactionShouldReturnBadRequest() {

        TransactionService service = mock(TransactionService.class);

        SampleController controller = new SampleController(service);

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN002");
        transaction.setCustomerId("CUST002");
        transaction.setAmount(BigDecimal.ZERO);
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("SUCCESS");

        when(service.saveTransaction(transaction))
                .thenThrow(new IllegalArgumentException("Amount must be greater than zero"));

        org.springframework.http.ResponseEntity<java.util.Map<String, String>> response =
                controller.handleIllegalArgumentException(
                        new IllegalArgumentException("Amount must be greater than zero")
                );

        assertEquals(400, response.getStatusCode().value());
        assertEquals(
                "Amount must be greater than zero",
                response.getBody().get("error")
        );
    }
    //Test 3 — GET existing transaction//retrive
    @Test
    void getTransactionShouldReturnTransaction() {

        TransactionService service = mock(TransactionService.class);

        SampleController controller = new SampleController(service);

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN003");
        transaction.setCustomerId("CUST003");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("SUCCESS");

        when(service.getTransaction("TXN003"))
                .thenReturn(transaction);

        Transaction result = controller.getTransaction("TXN003");

        assertEquals("TXN003", result.getTransactionId());
        assertEquals("CUST003", result.getCustomerId());
        assertEquals(new BigDecimal("500.00"), result.getAmount());

        verify(service).getTransaction("TXN003");
    }
    //Test 4 — Update Transaction Status  //PUT
    @Test
    void updateTransactionStatusShouldReturnUpdatedTransaction() {

        TransactionService service = mock(TransactionService.class);

        SampleController controller = new SampleController(service);

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN004");
        transaction.setCustomerId("CUST004");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("FAILED");

        when(service.updateTransactionStatus("TXN004", "FAILED"))
                .thenReturn(transaction);

        Transaction result =
                controller.updateTransactionStatus("TXN004", "FAILED");

        assertEquals("TXN004", result.getTransactionId());
        assertEquals("FAILED", result.getTransactionStatus());

        verify(service).updateTransactionStatus("TXN004", "FAILED");
    }
    //Test 5 — Get all transactions for a customer
    @Test
    void getTransactionsByCustomerShouldReturnTransactions() {

        TransactionService service = mock(TransactionService.class);

        SampleController controller = new SampleController(service);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("TXN005");
        transaction1.setCustomerId("CUST005");
        transaction1.setAmount(new BigDecimal("500.00"));
        transaction1.setCurrency("INR");
        transaction1.setTransactionType("PAYMENT");
        transaction1.setTransactionStatus("SUCCESS");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("TXN006");
        transaction2.setCustomerId("CUST005");
        transaction2.setAmount(new BigDecimal("1000.00"));
        transaction2.setCurrency("INR");
        transaction2.setTransactionType("PAYMENT");
        transaction2.setTransactionStatus("SUCCESS");

        when(service.getTransactionsByCustomer("CUST005"))
                .thenReturn(java.util.List.of(transaction1, transaction2));

        java.util.List<Transaction> result =
                controller.getTransactionsByCustomer("CUST005");

        assertEquals(2, result.size());
        assertEquals("TXN005", result.get(0).getTransactionId());
        assertEquals("TXN006", result.get(1).getTransactionId());

        verify(service).getTransactionsByCustomer("CUST005");
    }
    //Test 6 — Blank Customer ID
    @Test
    void getTransactionsByCustomerWithBlankIdShouldBeRejected() {

        TransactionService service = mock(TransactionService.class);

        SampleController controller = new SampleController(service);

        when(service.getTransactionsByCustomer(""))
                .thenThrow(new IllegalArgumentException("Customer ID is required"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.getTransactionsByCustomer("")
        );

        assertEquals(
                "Customer ID is required",
                exception.getMessage()
        );

        verify(service).getTransactionsByCustomer("");
    }
}