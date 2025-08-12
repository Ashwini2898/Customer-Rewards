package com.rewardsystem.rewardmanager.rewardService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.mapper.TransactionMapper;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardRepository.TransactionRepositoryDao;

/**
 * 
 * This is a Service class which contains business logic for Transaction entity to perform operations
 *
 */
@Service
public class TransactionServiceImpl {

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
	 * getTransactionsAllTransaction method of DAO layer to get all transactions 
	 * @return list of all transaction
	 * @throws InvalidTransactionException
	 */
	public List<TransactionDTO> getAllTransactions() throws InvalidTransactionException{
		try {

			List<Transaction> transactions = transactionRepository.findAll();
			for (Transaction t : transactions) {
				if (t.getAwardedPoints() == null) {
					double points = calculatePoints(t.getAmountSpent() != null ? t.getAmountSpent() : 0.0);
					t.setAwardedPoints(points);
					transactionRepository.save(t);  // save and commit inside transaction
				}
			}
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
			for (Transaction t : transactions) {
				if (t.getAwardedPoints() == null) {
					double points = calculatePoints(t.getAmountSpent() != null ? t.getAmountSpent() : 0.0);
					t.setAwardedPoints(points);
					transactionRepository.save(t); // persist changes
				}
			}
			return transactionMapper.toSummaryDTO(transactions);
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}
}