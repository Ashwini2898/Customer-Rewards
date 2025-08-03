package com.rewardsystem.rewardmanager.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a customer in the reward system.
 * A customer can make transactions and earn reward points.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="customer_master")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	@NotBlank(message="Please enter your name")
	@Size(min=2, max=30,message=" Name should have atleast 2 characters")
	@Column(name = "customer_name")
	private String custName;

	@NotNull
	@NotBlank(message="Please enter your phone number")
	@Pattern(regexp="([7-9][0-9]{9})")
	@Column(name = "customer_mobile")
	private String custMobile;

	@Column(name = "total_spent")
	private Double totalSpent;

	@Column(name = "reward_points")
	private Integer rewardPoints =0;

	@OneToMany(mappedBy = "customer")
	private List<Transaction> transaction;

	// Getters Block
	public Long getCustomerId() {
		return customerId;
	}

	public String getCustName() {
		return custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}

	// Setters Block
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile=custMobile;

	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
}
