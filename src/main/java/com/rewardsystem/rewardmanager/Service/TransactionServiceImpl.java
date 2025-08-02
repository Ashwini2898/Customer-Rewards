package com.rewardsystem.rewardmanager.Service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Entity.Transaction;
import com.rewardsystem.rewardmanager.Exception.InvalidTransactionException;
import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.Repository.TransactionRepositoryDao;

import jakarta.transaction.Transactional;

/**
 * 
 * This is a Service class which contains business logic for Transaction entity to perform operations
 *
 */

@Service
public class TransactionServiceImpl {

	@Autowired
	private  CustomerRepositoryDao customerDao;
	@Autowired
	private  TransactionRepositoryDao transactionRepository;

	/**
	 * method to calculate Points for total expenditure
	 */

	private int calculatePoints(double amount) {
		int points = 0;

		if (amount > 100) {
			points += (int)((amount - 100) * 2); // > 100$ → 2 points
			points += 50; // fixed 50 points for $50–$100 range
		} else if (amount > 50) {
			points += (int)(amount - 50); // $50–$100 → 1 point per dollar
		}

		return points;
	}

	/**
	 * method to create Transaction to maintain transaction list
	 */

	@Transactional
	public Transaction createTransaction(Long customerId, double amount) throws InvalidTransactionException{
		try {
			Customer customer = customerDao.findById(customerId)
					.orElseThrow(() -> new RuntimeException("Customer not found"));

			Integer points = calculatePoints(amount); 

			Transaction txn = new Transaction();
			txn.setCustomer(customer);
			txn.setAmountSpent(amount);
			txn.setAwardedPoints(points);
			txn.setDate(LocalDateTime.now());

			transactionRepository.save(txn);

			Integer current = customer.getRewardPoints() == null ? 0 : customer.getRewardPoints();
			Double totalSpent=customer.getTotalSpent()== null ? 0 : customer.getTotalSpent();
			customer.setRewardPoints(current + points);
			customer.setTotalSpent(totalSpent+amount);

			customerDao.save(customer);  

			return txn;
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new InvalidTransactionException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * method to get total points of a customer
	 */
	public Integer getCustomerPoints(Long customerId) throws InvalidTransactionException {
		try {
			Customer customer = customerDao.findById(customerId)
					.orElseThrow(() -> new RuntimeException("Customer not found"));
			return customer.getRewardPoints() != null ? customer.getRewardPoints() : 0;
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new InvalidTransactionException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}


	

}


