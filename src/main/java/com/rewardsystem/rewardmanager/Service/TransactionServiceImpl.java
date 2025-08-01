package com.rewardsystem.rewardmanager.Service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Entity.Transaction;

import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.Repository.TransactionRepositoryDao;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl {

	@Autowired
	private  CustomerRepositoryDao customerDao;
	@Autowired
	private  TransactionRepositoryDao transactionRepository;


	public TransactionServiceImpl(CustomerRepositoryDao customerDao,
			TransactionRepositoryDao transactionRepository) {
		this.customerDao = customerDao;
		this.transactionRepository = transactionRepository;
	}
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

	@Transactional
	public Transaction createTransaction(int customerId, double amount) {
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

		customer.setRewardPoints(current + points);

		customerDao.save(customer);  

		return txn;
	}


	public Integer getCustomerPoints(Integer customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		System.out.println("Reward Points: " + customer.getRewardPoints());
		return customer.getRewardPoints() != null ? customer.getRewardPoints() : 0;
	}
}


