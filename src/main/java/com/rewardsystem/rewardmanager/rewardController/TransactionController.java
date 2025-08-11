
package com.rewardsystem.rewardmanager.rewardController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardService.TransactionServiceImpl;

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

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	/**
	 * All transaction are returned as a list of transaction
	 * @return 
	 * @throws InvalidTransactionException 
	 */
	@GetMapping("/getAllTransactions")
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() throws InvalidTransactionException {
			return ResponseEntity.ok(transactionService.getAllTransactions());
	}

	/**
	 * 
	 * @param id is customerID to get the transaction between given period of time
	 * @param fromDate is the start date of the transaction period, in <code>dd-MM-yyyy</code> format
	 * @param toDate is the end date of the transaction period, in <code>dd-MM-yyyy</code> format
	 * @return list of transaction within given fromDate and toDate
	 * @throws InvalidTransactionException 
	 */
	@GetMapping("/{id}/getTransactionByCustomerID")
	public ResponseEntity<List<TransactionSummaryDTO>> getCustomerTransactions(@PathVariable Long id, 
			@RequestParam(required=false)  @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fromDate ,
			@RequestParam (required=false)  @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate toDate ) throws InvalidTransactionException {
			if (fromDate != null && toDate != null && toDate.isBefore(fromDate)) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST,
						"'toDate' should not be earlier than 'fromDate'"
						);
			}			
			LocalDateTime start = (fromDate != null) ? fromDate.atStartOfDay() : LocalDate.now().minusMonths(3).atStartOfDay();
		    LocalDateTime end = (toDate != null) ? toDate.atTime(23, 59, 59) : LocalDateTime.now();
		    return ResponseEntity.ok(transactionService.getCustomerTransactions(id,start, end));			    
	}		
}