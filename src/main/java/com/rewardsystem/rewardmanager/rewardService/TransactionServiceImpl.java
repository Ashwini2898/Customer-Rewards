package com.rewardsystem.rewardmanager.rewardService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.mapper.TransactionMapper;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
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
	private  TransactionRepositoryDao transactionRepository;

	@Autowired
	private TransactionMapper transactionMapper;
	
	private static final double POINTS_THRESHOLD_1 = 50;
    private static final double POINTS_THRESHOLD_2 = 100;

	/**
	 * method to calculate Points for total expenditure
	 */
	private static double calculatePoints(double amount) {
		if (amount <= POINTS_THRESHOLD_1) return 0;
        if (amount <= POINTS_THRESHOLD_2) return amount - POINTS_THRESHOLD_1;
        return (POINTS_THRESHOLD_2 - POINTS_THRESHOLD_1) + (amount - POINTS_THRESHOLD_2) * 2;
	}

	private void updatePointsIfNeeded(Transaction t) {
		if (t.getAwardedPoints() == null) {
			t.setAwardedPoints(calculatePoints(Objects.requireNonNullElse(t.getAmountSpent(), 0.0)));
		}
	}

	/**
	 * getTransactionsAllTransaction method of DAO layer to get all transactions 
	 * @return list of all transaction
	 * @throws InvalidTransactionException
	 */
	@Transactional
	public List<TransactionDTO> getAllTransactions() throws InvalidTransactionException{
		try {

			List<Transaction> transactions = transactionRepository.findAll();
			transactions.forEach(this::updatePointsIfNeeded);
			transactionRepository.saveAll(transactions);
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
	@Transactional
	public List<TransactionSummaryDTO> getCustomerTransactions(Long customerId, LocalDateTime fromDate, LocalDateTime toDate) throws InvalidTransactionException{
		try {			
			List<Transaction> transactions = transactionRepository
					.findAllByCustomer_CustomerIdAndDateBetween(customerId, fromDate, toDate);
			transactions.forEach(this::updatePointsIfNeeded);
			transactionRepository.saveAll(transactions);
			return transactionMapper.toSummaryDTO(transactions);
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}
}