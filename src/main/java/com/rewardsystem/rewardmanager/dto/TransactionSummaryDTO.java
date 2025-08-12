package com.rewardsystem.rewardmanager.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class TransactionSummaryDTO {
	
	@NotNull
	private Long transactionId;
	
	@NotNull
    private Double amountSpent;
	
	@NotNull
    private LocalDateTime date;
	
	@NotNull
    private Double awardedPoints;
    
	public TransactionSummaryDTO() {}

	public TransactionSummaryDTO(@NotNull Long transactionId,@NotNull Double amountSpent,@NotNull LocalDateTime date,@NotNull Double awardedPoints) {
		this.transactionId = transactionId;
		this.amountSpent = amountSpent;
		this.date = date;
		this.awardedPoints = awardedPoints;
	}

	public void setTransactionId(@NotNull Long transactionId) {
		this.transactionId = transactionId;
	}

	public void setAmountSpent(@NotNull Double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public void setDate(@NotNull LocalDateTime date) {
		this.date = date;
	}

	public void setAwardedPoints(@NotNull Double awardedPoints) {
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

	public Double getAwardedPoints() {
		return awardedPoints;
	}	
	
}
