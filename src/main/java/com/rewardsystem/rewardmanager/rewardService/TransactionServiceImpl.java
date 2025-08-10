package com.rewardsystem.rewardmanager.rewardService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.mapper.TransactionMapper;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardRepository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.rewardRepository.TransactionRepositoryDao;

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

	@Autowired
	private TransactionMapper transactionMapper;
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
	 * @param customerId to make transaction to specific customer using id which is unique key
	 * @param amount refers to transaction amount
	 * @return 
	 * @throws InvalidTransactionException
	 */
	@Transactional
	public TransactionDTO createTransaction(Long customerId, double amount) throws InvalidTransactionException{
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

			return transactionMapper.toDTO(txn);
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * 
	 * @param customerId to get all points of specific customer
	 * @return
	 * @throws InvalidTransactionException
	 */
	public Integer getCustomerPoints(Long customerId) throws InvalidTransactionException {
		try {
			Customer customer = customerDao.findById(customerId)
					.orElseThrow(() -> new RuntimeException("Customer not found"));
			return customer.getRewardPoints() != null ? customer.getRewardPoints() : 0;
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * getTransactionsAllTransaction method of DAO layer to get all transactions 
	 * @return list of all transaction
	 * @throws InvalidTransactionException
	 */
	public List<TransactionDTO> getAllTransactions() throws InvalidTransactionException{
		try {

			List<Transaction> transactions = transactionRepository.findAll();
			return transactionMapper.toDTO(transactions);
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * 
	 @param id is customerID to get the transaction between given period of time
	 * @param fromDate is the start date of the transaction period, in <code>dd-MM-yyyy</code> format
	 * @param toDate is the end date of the transaction period, in <code>dd-MM-yyyy</code> format
	 * @return list of transaction within given fromDate and toDate
	 * @throws InvalidTransactionException
	 */
	public List<TransactionSummaryDTO> getCustomerTransactions(Long customerId, LocalDateTime fromDate, LocalDateTime toDate) throws InvalidTransactionException{
		try {			

			List<Transaction> transactions = transactionRepository
					.findAllByCustomer_CustomerIdAndDateBetween(customerId, fromDate, toDate);
			return transactionMapper.toSummaryDTO(transactions);
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * 
	 *@param id to get all transaction specific to a customer
	 * @return list of transaction 
	 * @throws InvalidTransactionException
	 */
	public List<TransactionSummaryDTO> getAllTransactionsByCustomerId(Long id) throws InvalidTransactionException {
		// TODO Auto-generated method stub
		try {			
			List<Transaction> transactions =transactionRepository.findAllByCustomer_CustomerId(id);
			return transactionMapper.toSummaryDTO(transactions);
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}
}