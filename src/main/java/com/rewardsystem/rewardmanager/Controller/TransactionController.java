package com.rewardsystem.rewardmanager.Controller;

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
import com.rewardsystem.rewardmanager.Service.TransactionServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/transactions/")
@Slf4j
public class TransactionController {
	
	@Autowired
	private TransactionServiceImpl transactionService;
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	
    @PostMapping("/transactions/")
    public String createTransaction(@RequestParam int customerId, @RequestParam double amount) 
    {
    	Transaction transaction=transactionService.createTransaction(customerId, amount);
        logger.info("Transaction added: " +transaction);
        return "Traction added successfully of " +amount +"$" ;
    }
    

    @GetMapping("/customers/{customerId}/points/")
    public String getCustomerPoints(@PathVariable int customerId)  {
    	 Integer points= transactionService.getCustomerPoints(customerId);
        logger.info("Points added are: " +points);
        return "total poits are: "+points;
    }
}