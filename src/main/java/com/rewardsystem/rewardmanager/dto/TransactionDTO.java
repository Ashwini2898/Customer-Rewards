package com.rewardsystem.rewardmanager.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
	private Long transactionId;
	private Double amountSpent;
	private Long customerId;
	private LocalDateTime date;
	private Integer awardedPoints;

	public TransactionDTO() {}

	public TransactionDTO(Long transactionId, Double amountSpent, Long customerId, LocalDateTime date, Integer awardedPoints) {
		this.transactionId = transactionId;
		this.amountSpent = amountSpent;
		this.customerId = customerId;
		this.date = date;
		this.awardedPoints = awardedPoints;
	}

	// Getters & Setters
	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Double getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(Double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getAwardedPoints() {
		return awardedPoints;
	}

	public void setAwardedPoints(Integer awardedPoints) {
		this.awardedPoints = awardedPoints;
	}

}
