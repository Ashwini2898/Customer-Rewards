package com.rewardsystem.rewardmanager.Service;


import java.time.LocalDateTime;
import java.util.List;

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
	public Transaction createTransaction(Long customerId, double amount) {
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


	public Integer getCustomerPoints(Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		return customer.getRewardPoints() != null ? customer.getRewardPoints() : 0;
	}
	
	public List<Transaction> getTransactionsForMonth(int year, int month) {
	    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
	    LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

	    return transactionRepository.findAllByDateBetween(start, end);
	}
	
	 public List<Transaction> getTransactionsForCustomerLastThreeMonths(Long customerId) {
	        LocalDateTime now = LocalDateTime.now();
	        LocalDateTime start = now.minusMonths(3).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
	        LocalDateTime end = now;

	        return transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(customerId, start, end);
	    }
	 
	 public List<Transaction> getTransactionsForCustomerLastOneMonth(Long customerId) {
	        LocalDateTime now = LocalDateTime.now();
	        LocalDateTime start = now.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
	        LocalDateTime end = now;

	        return transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(customerId, start, end);
	    }
	
}


