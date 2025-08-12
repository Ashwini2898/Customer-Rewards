package com.rewardsystem.rewardmanager.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;

/**
 *Data Transfer Object (DTO) for transferring transaction data between different layers
 *
 */
public class TransactionDTO {
	
	@NotNull
	private Long customerId;
	
	@NotNull
    private String customerName;
	
	@NotNull
    private Double totalPoints;
	
	@NotNull
    private Map<String, Double> monthlyPoints; 
	
	@NotNull
    private List<TransactionSummaryDTO> transactions;
	
	public TransactionDTO(){};
	
	public TransactionDTO(@NotNull Long customerId, @NotNull String customerName, @NotNull Double totalPoints,
			@NotNull Map<String, Double> monthlyPoints, @NotNull List<TransactionSummaryDTO> transactions) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.totalPoints = totalPoints;
		this.monthlyPoints = monthlyPoints;
		this.transactions = transactions;
	}

	//Setters block
	public void setCustomerId( @NotNull Long customerId) {
		this.customerId = customerId;
	}

	public void setCustomerName(@NotNull String customerName) {
		this.customerName = customerName;
	}

	public void setTotalPoints(@NotNull Double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public void setMonthlyPoints(@NotNull Map<String, Double> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public void setTransactions(@NotNull List<TransactionSummaryDTO> transactions) {
		this.transactions = transactions;
	}

	//Getter Block
	public Long getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Double getTotalPoints() {
		return totalPoints;
	}

	public Map<String, Double> getMonthlyPoints() {
		return monthlyPoints;
	}

	public List<TransactionSummaryDTO> getTransactions() {
		return transactions;
	} 
	
}
