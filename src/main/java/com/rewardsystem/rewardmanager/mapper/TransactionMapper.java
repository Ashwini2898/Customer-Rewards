package com.rewardsystem.rewardmanager.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;

/**
 * 
 * Mapper class for converting between Transaction Entity and TransactionDTO objects
 *
 */
@Component
public class TransactionMapper {

	public TransactionDTO toDTO(Transaction transaction) {

		TransactionDTO dto = new TransactionDTO();
		dto.setAmountSpent(transaction.getAmountSpent());
		dto.setAwardedPoints(transaction.getAwardedPoints());
		dto.setDate(transaction.getDate());
		dto.setTransactionId(transaction.getTransactionId());

		if (transaction.getCustomerId() != null) {
			dto.setCustomerId(transaction.getCustomerId());

		}
		return dto;
	}

	public List<TransactionDTO> toDTO(List<Transaction> transactions) {
		return transactions.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}	
}
