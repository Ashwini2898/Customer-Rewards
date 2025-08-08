package com.rewardsystem.rewardmanager.rewardService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.dto.CustomerDTO;
import com.rewardsystem.rewardmanager.mapper.CustomerMapper;
import com.rewardsystem.rewardmanager.rewardException.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.rewardRepository.CustomerRepositoryDao;

@Service
public class CustomerServiceImpl {

	@Autowired
	CustomerRepositoryDao customerDao;

	@Autowired
	private CustomerMapper customerMapper;

	/**
	 * calling getCustomerById method of DAO layer
	 */
	public CustomerDTO getCustomerById(Long customerId) throws CustomerNotFoundException 
	{

		return customerDao.findById(customerId)
				.map(customerMapper::toDTO)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
	}


}
