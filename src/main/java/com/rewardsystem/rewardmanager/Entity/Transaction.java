package com.rewardsystem.rewardmanager.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="transaction_manager")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
	private Integer transactionId;

	@NotNull
	@Column(name = "amount_spent")
    private Double amountSpent;

    @ManyToOne
	@JoinColumn(name = "customer_id",nullable = false)
	private Customer customer;

    @Column(name="transaction_date",nullable = false)
    private LocalDateTime date = LocalDateTime.now();

}
