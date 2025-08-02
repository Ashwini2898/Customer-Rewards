package com.rewardsystem.rewardmanager.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a Transaction in the reward system.
 * This class can keep record of all transactions done by a customer and award points for each transactions.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="transaction_manager")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
	private Long transactionId;

	
	@Column(name = "amount_spent")
    private Double amountSpent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id",nullable = false)
	private Customer customer;
    
    @Column(name="transaction_date",nullable = false)
    private LocalDateTime date = LocalDateTime.now();
    
    @Column(name = "awarded_points")
	private Integer awardedPoints;

    public Long getTransactionId() {
		return transactionId;
	}

	public Integer getAwardedPoints() {
		return awardedPoints;
	}

	public void setAwardedPoints(Integer awardedPoints) {
		this.awardedPoints = awardedPoints;
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

	

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setCustomer(Customer customer) {
	    this.customer = customer;
	}

	

	

	

	

	
}
