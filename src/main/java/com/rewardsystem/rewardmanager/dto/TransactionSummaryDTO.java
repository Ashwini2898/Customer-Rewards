package com.rewardsystem.rewardmanager.dto;

import java.time.LocalDateTime;

public class TransactionSummaryDTO {
	private Long transactionId;
    private Double amountSpent;
    private LocalDateTime date;
    private Integer awardedPoints;
    
    
	public TransactionSummaryDTO() {}

	public TransactionSummaryDTO(Long transactionId, Double amountSpent, LocalDateTime date, Integer awardedPoints) {
		super();
		this.transactionId = transactionId;
		this.amountSpent = amountSpent;
		this.date = date;
		this.awardedPoints = awardedPoints;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public void setAmountSpent(Double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setAwardedPoints(Integer awardedPoints) {
		this.awardedPoints = awardedPoints;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public Double getAmountSpent() {
		return amountSpent;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Integer getAwardedPoints() {
		return awardedPoints;
	}

	
}
