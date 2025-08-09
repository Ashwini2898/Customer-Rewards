package com.rewardsystem.rewardmanager.mapper;

import org.springframework.stereotype.Component;

import com.rewardsystem.rewardmanager.dto.CustomerDTO;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;

/**
 * 
 * Mapper class for converting between Customer Entity and CustomerDTO objects
 *
 */
@Component
public class CustomerMapper {

	public CustomerDTO toDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setCustName(customer.getCustName());
		dto.setRewardPoints(customer.getRewardPoints());
		dto.setTotalSpent(customer.getTotalSpent());
		dto.setCustMobile(customer.getCustMobile());
		dto.setCustomerId(customer.getCustomerId());
		return dto;
	}

}