package com.rewardsystem.rewardmanager.Service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Entity.Transaction;

import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.Repository.TransactionRepositoryDao;

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
    public Transaction createTransaction(int customerId, double amount) {
                
    		Customer customer = customerDao.findById(customerId)
    	            .orElseThrow(() -> new RuntimeException("Customer not found"));
		

        int points = calculatePoints(amount); 

        Transaction txn = new Transaction();
        txn.setCustomer(customer);
        txn.setAmountSpent(amount);
        txn.setAwardedPoints(points);
        txn.setDate(java.time.LocalDateTime.now());

        transactionRepository.save(txn);

        customer.setRewardPoints(customer.getRewardPoints() + points);
        customerDao.save(customer);

        return txn;
        
    	
    }

    public Integer getCustomerPoints(Integer customerId) {
        Customer customer = customerDao.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getRewardPoints();
    }
}


