package com.rewardsystem.rewardmanager.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
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

	/**
	 * Helper method to map a single Transaction to TransactionSummaryDTO
	 * @throws IllegalArgumentException 
	 */
	private TransactionSummaryDTO mapSingleTransactionToTransactionSummaryDTO(Transaction t) {
		if (t == null) {
			throw new IllegalArgumentException("Transaction cannot be null");
		}
		if (t.getDate() == null) {
			throw new IllegalArgumentException("Transaction date cannot be null");
		}

		return new TransactionSummaryDTO(
				t.getTransactionId() != null ? t.getTransactionId() : 0L,
						t.getAmountSpent() != null ? t.getAmountSpent() : 0.0,
								t.getDate(),
								t.getAwardedPoints()
				);
	}

	public List<TransactionSummaryDTO> toSummaryDTO(List<Transaction> transactions) {
		if (transactions == null) {
			throw new IllegalArgumentException("Transactions list cannot be null while mapping rewards.");
		}

		return transactions.stream()
				.filter(Objects::nonNull)
				.map(this::mapSingleTransactionToTransactionSummaryDTO)
				.collect(Collectors.toList());
	}

	public List<TransactionDTO> toDTO(List<Transaction> transactions)  {

		for (Transaction t : transactions) {
			if (t != null && t.getCustomerId() == null) {
				throw new IllegalArgumentException("Customer ID cannot be null while grouping.");
			}
		}

		// Group transactions by customerId
		Map<Long, List<Transaction>> transactionsByCustomer =
				transactions.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(Transaction::getCustomerId));

		// Build TransactionDTO list
		return transactionsByCustomer.entrySet().stream().map(entry -> {
			Long customerId = entry.getKey();

			Map<Long, String> customerNames = customerRepository.findAllById(transactionsByCustomer.keySet())
					.stream()
					.collect(Collectors.toMap(Customer::getCustomerId, Customer::getCustName));

			String customerName = Optional.ofNullable(customerNames.get(customerId))
					.filter(name -> !name.isBlank())
					.orElse("Unknown");

			List<Transaction> customerTransactions = entry.getValue();

			// Total points
			double totalPoints = customerTransactions.stream()
					.mapToDouble(t -> t.getAwardedPoints() != null ? t.getAwardedPoints() : 0.0)
					.sum();

			// Transactions summary
			List<TransactionSummaryDTO> transactionSummaries=toSummaryDTO(customerTransactions);

			return new TransactionDTO(
					customerId,
					customerName,
					totalPoints,
					transactionSummaries
					);
		}).collect(Collectors.toList());
	}

}
