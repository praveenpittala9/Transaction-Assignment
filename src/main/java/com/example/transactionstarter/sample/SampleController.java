package com.example.transactionstarter.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.service.TransactionService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable; //get transction 
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;  //update transction 
import java.util.List;//get transcation 

import java.util.Map;

@RestController
public class SampleController {

	private final TransactionService transactionService;

	public SampleController(TransactionService transactionService) {
	    this.transactionService = transactionService;
	}
	//create transcation
	@PostMapping("/api/transactions")   //local host http://localhost:8080/api/transactions
	public Transaction createTransaction(@RequestBody Transaction transaction) {
	    return transactionService.saveTransaction(transaction);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
	        IllegalArgumentException ex) {

	    return ResponseEntity
	            .badRequest()
	            .body(Map.of("error", ex.getMessage()));
	}
    @GetMapping("/api/sample")            
    public Map<String, String> sample() {
        return Map.of("message", "Starter project is running");
    }
    //get transcation 
    @GetMapping("/api/transactions/{transactionId}")//get local host 
    public Transaction getTransaction(@PathVariable String transactionId) {
        return transactionService.getTransaction(transactionId);
    }
    //update transcation 
    @PutMapping("/api/transactions/{transactionId}/status")
    public Transaction updateTransactionStatus(
            @PathVariable String transactionId,
            @RequestParam String status) {

        return transactionService.updateTransactionStatus(transactionId, status);
    }
    //get transcation 
    @GetMapping("/api/customers/{customerId}/transactions")
    public List<Transaction> getTransactionsByCustomer(
            @PathVariable String customerId) {

        return transactionService.getTransactionsByCustomer(customerId);
    }
}
