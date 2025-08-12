package com.rewardsystem.rewardmanager.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardRepository.CustomerRepositoryDao;

/**
 * 
 * Mapper class for converting between Transaction Entity and TransactionDTO objects
 *
 */
@Component
public class TransactionMapper {

	@Autowired
	private CustomerRepositoryDao customerRepository;

	public List<TransactionSummaryDTO> toSummaryDTO(List<Transaction> transactions) throws InvalidTransactionException {

		if (transactions == null) {
			throw new InvalidTransactionException("Customer cannot be null while mapping rewards.");
		}

		return transactions.stream()
				.filter(Objects::nonNull)
				.map(t -> new TransactionSummaryDTO(
						t.getTransactionId() != null ? t.getTransactionId() : 0L,
								t.getAmountSpent() != null ? t.getAmountSpent() : 0.0,
										t.getDate(),
										t.getAwardedPoints() != null ? t.getAwardedPoints() : 0.0
						))
				.collect(Collectors.toList());
	}

	public List<TransactionDTO> toDTO(List<Transaction> transactions) {
		if (transactions == null) {
			throw new IllegalArgumentException("Transactions list cannot be null");
		}

		// Group transactions by customerId
		Map<Long, List<Transaction>> transactionsByCustomer =
				transactions.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(Transaction::getCustomerId));

		// Build TransactionDTO list
		return transactionsByCustomer.entrySet().stream().map(entry -> {
			Long customerId = entry.getKey();
			if (entry.getKey() == null) {
				throw new IllegalArgumentException("Customer ID cannot be null while mapping transactions.");
			}
			String customerName = customerRepository.findById(customerId)
					.map(Customer::getCustName)
					.filter(name -> !name.isBlank())
					.orElse("Unknown");
			List<Transaction> customerTransactions = entry.getValue();

			// Total points
			double totalPoints = customerTransactions.stream()
					.mapToDouble(t -> t.getAwardedPoints() != null ? t.getAwardedPoints() : 0.0)
					.sum();

			// Monthly points
			Map<String, Double> monthlyPoints = customerTransactions.stream()
					.filter(t -> t != null && t.getDate() != null)
					.collect(Collectors.groupingBy(
							t -> t.getDate().getYear() + "-" +
									String.format("%02d", t.getDate().getMonthValue()),
									Collectors.summingDouble(t -> t.getAwardedPoints() != null ? t.getAwardedPoints() : 0.0)
							));

			// Transactions summary

			List<TransactionSummaryDTO> transactionSummaries;
			try {
				transactionSummaries = toSummaryDTO(customerTransactions);
			} catch (InvalidTransactionException e) {
				System.err.println("Error mapping transactions for customer " + customerId + ": " + e.getMessage());
				transactionSummaries = Collections.emptyList(); // fallback to empty list
			}

			return new TransactionDTO(
					customerId,
					customerName,
					totalPoints,
					monthlyPoints,
					transactionSummaries
					);
		}).collect(Collectors.toList());
	}

}
