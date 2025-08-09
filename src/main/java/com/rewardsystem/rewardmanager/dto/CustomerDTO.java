package com.rewardsystem.rewardmanager.dto;

/**
 *Data Transfer Object (DTO) for transferring customer data between different layers
 *
 */
public class CustomerDTO {
	private Long customerId;
	private String custName;
	private String custMobile;
	private Double totalSpent;
	private Integer rewardPoints;

	// Constructors
	public CustomerDTO() {}

	public CustomerDTO(Long customerId, String custName, String custMobile, Double totalSpent, Integer rewardPoints) {
		this.customerId = customerId;
		this.custName = custName;
		this.custMobile = custMobile;
		this.totalSpent = totalSpent;
		this.rewardPoints = rewardPoints;
	}

	// Getters and Setters
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
}

