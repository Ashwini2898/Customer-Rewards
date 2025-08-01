package com.rewardsystem.rewardmanager.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rewardsystem.rewardmanager.Entity.Transaction;
import com.rewardsystem.rewardmanager.Exception.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.Exception.InvalidTransactionException;
import com.rewardsystem.rewardmanager.Service.CustomerServiceImpl;
import com.rewardsystem.rewardmanager.Service.TransactionServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/transactions/")
@Slf4j
public class TransactionController {

	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	CustomerServiceImpl customerService;

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


	@PostMapping("/transactions/")
	public String createTransaction(@RequestParam Long customerId, @RequestParam double amount) 
	{
		Transaction transaction=transactionService.createTransaction(customerId, amount);
		logger.info("Transaction added: " +transaction);
		return "Traction added successfully of " +amount +"$" ;
	}


	@GetMapping("/customers/{customerId}/points/")
	public String getCustomerPoints(@PathVariable Long customerId)  {
		Integer points= transactionService.getCustomerPoints(customerId);
		logger.info("Points added are: " +points);
		return "total poits are: "+points;
	}

	@GetMapping("/transactions/month")
	public ResponseEntity<List<Transaction>> getMonthlyTransactions(@RequestParam int year,@RequestParam int month)  {
		try {
			List<Transaction> transactions = customerService.getTransactionsForMonth(year, month);
			return ResponseEntity.ok(transactions);
		}
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}


	@GetMapping("/transactions/last-three-months")
	public ResponseEntity<List<Transaction>> getAllTransactionsForLastThreeMonths() {
		try {
			return ResponseEntity.ok(customerService.getTransactionsForLastThreeMonths());
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}

	@GetMapping("/customers/{id}/transactions/last-three-months")
	public ResponseEntity<List<Transaction>> getCustomerTransactionsForLastThreeMonths(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(customerService.getCustomerTransactionsForLastThreeMonths(id));
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}

	@GetMapping("/customers/{id}/transactions/last-one-months")
	public ResponseEntity<List<Transaction>> getCustomerTransactionsForLastOneMonth(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(customerService.getCustomerTransactionsForLastOneMonth(id));
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}


}