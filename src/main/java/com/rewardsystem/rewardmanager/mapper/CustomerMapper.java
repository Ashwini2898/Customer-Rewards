package com.rewardsystem.rewardmanager.mapper;

import org.springframework.stereotype.Component;

import com.rewardsystem.rewardmanager.dto.CustomerDTO;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;

@Component
public class CustomerMapper {

	public CustomerDTO toDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setCustName(customer.getCustName());
		dto.setRewardPoints(customer.getRewardPoints());
		dto.setTotalSpent(customer.getTotalSpent());
		dto.setCustMobile(customer.getCustMobile());
		dto.setCustomerId(customer.getCustomerId());
		// do NOT set customerId if you want to hide it
		return dto;
	}

}