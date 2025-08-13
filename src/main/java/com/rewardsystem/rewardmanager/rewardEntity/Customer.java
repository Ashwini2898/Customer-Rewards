package com.rewardsystem.rewardmanager.rewardEntity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity
@Table(name="customer_master")
public class Customer {

	@Id
	@NotNull
	@Column(name = "customer_id")
	private Long customerId;

	@NotBlank(message="Please enter your name")
	@Size(min=2, max=30,message=" Name should have atleast 2 characters")
	@Column(name = "customer_name")
	private String custName;

	@NotBlank(message="Please enter your phone number")
	@Pattern(regexp="([7-9][0-9]{9})")
	@Column(name = "customer_mobile")
	private String custMobile;

	@NotNull
	@Column(name = "total_spent")
	private Double totalSpent;

	@NotNull
	@Column(name = "total_points")
	private Double totalPoints =0.0;

	@NotNull
	@OneToMany(mappedBy = "customer")
	private List<Transaction> transactions;

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

	public Double getRewardPoints() {
		return totalPoints;
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

	public void setRewardPoints(Double rewardPoints) {
		this.totalPoints = rewardPoints;
	}

}
