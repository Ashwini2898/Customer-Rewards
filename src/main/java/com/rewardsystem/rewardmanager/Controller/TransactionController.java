

package com.rewardsystem.rewardmanager.Controller;


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
import com.rewardsystem.rewardmanager.Exception.InvalidTransactionException;
import com.rewardsystem.rewardmanager.Service.CustomerServiceImpl;
import com.rewardsystem.rewardmanager.Service.TransactionServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller to handle transaction related endpoints.
 */
@RestController
@RequestMapping("/api/transactions/")
@Slf4j
public class TransactionController {

	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	CustomerServiceImpl customerService;

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	/**
	 * get all transactions
	 * @param customerId
	 * @param amount
	 * @return
	 */
	@PostMapping("/new-transaction/")
	public String createTransaction(@RequestParam Long customerId, @RequestParam double amount) 
	{
		try {
			Transaction transaction;
			transaction = transactionService.createTransaction(customerId, amount);
			logger.info("Transaction added: " +transaction);
			return "Traction added successfully of " +amount +"$" ;
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}

	}

	/**
	 * get points for a  customer
	 * @param customerId
	 * @return
	 */
	@GetMapping("/customers/{customerId}/points/")
	public String getCustomerPoints(@PathVariable Long customerId)  {
		try {
			Integer points= transactionService.getCustomerPoints(customerId);
			logger.info("Points added fot customer id "+customerId+" are: " +points);
			return "total poits are: "+points;
		}
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}

	/**
	 * get all transactions by year and month 
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/month")
	public ResponseEntity<List<Transaction>> getMonthlyTransactions(@RequestParam int year,@RequestParam int month)  {
		try {
			List<Transaction> transactions = customerService.getTransactionsForMonth(year, month);
			logger.info("Returning all transaction for last one month of all customers");
			return ResponseEntity.ok(transactions);
		}
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}

	/**
	 * get all transactions by for last three months 
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/last-three-months")
	public ResponseEntity<List<Transaction>> getAllTransactionsForLastThreeMonths() {
		try {
			logger.info("Returning all Transactions for last three months of all customers");
			return ResponseEntity.ok(customerService.getTransactionsForLastThreeMonths());
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}

	/**
	 * get transactions for last three months for a customer
	 * @param id
	 * @return
	 */
	@GetMapping("/customers/{id}/last-three-months")
	public ResponseEntity<List<Transaction>> getCustomerTransactionsForLastThreeMonths(@PathVariable Long id) {
		try {
			logger.info("Returning all transaction for last three months of customer by its ID");
			return ResponseEntity.ok(customerService.getCustomerTransactionsForLastThreeMonths(id));
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}

	/**
	 * get transactions for last one months for a customer
	 * @param id
	 * @return
	 */
	@GetMapping("/customers/{id}/last-one-months")
	public ResponseEntity<List<Transaction>> getCustomerTransactionsForLastOneMonth(@PathVariable Long id) {
		try {
			logger.info("Returning all transaction for last one month of customer by its ID");
			return ResponseEntity.ok(customerService.getCustomerTransactionsForLastOneMonth(id));
		} 
		catch(InvalidTransactionException invalidTransactionException) 
		{
			logger.error(invalidTransactionException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,invalidTransactionException.getMessage());
		}
	}
}