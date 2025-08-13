package com.rewardsystem.rewardmanager.rewardEntity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Transaction in the reward system.
 * This class can keep record of all transactions done by a customer and award points for each transactions.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transaction_manager")
public class Transaction {

	@Id
	@NotNull
	@Column(name = "transaction_id")
	private Long transactionId;

	@NotNull
	@Column(name = "amount_spent")
	private Double amountSpent;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "customer_id",nullable = false)
	private Customer customer;

	@NotNull
	@Column(name="transaction_date",nullable = false)
	private LocalDateTime date = LocalDateTime.now();

	@NotNull
	@Column(name = "awarded_points")
	private Double awardedPoints;

	public Long getTransactionId() {
		return transactionId;
	}

	public Double getAwardedPoints() {
		return awardedPoints;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Double getAmountSpent() {
		return amountSpent;
	}

	@JsonProperty("customerId")
	public Long getCustomerId() {
		return customer != null ? customer.getCustomerId() : null;
	}	

	public void setAwardedPoints(Double awardedPoints) {
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

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Transaction)) return false;
	    Transaction that = (Transaction) o;
	    return transactionId != null && transactionId.equals(that.transactionId);
	}

	@Override
	public int hashCode() {
	    return getClass().hashCode();
	}

}
