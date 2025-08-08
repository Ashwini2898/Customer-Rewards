package com.rewardsystem.rewardmanager.rewardController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rewardsystem.rewardmanager.dto.CustomerDTO;
import com.rewardsystem.rewardmanager.rewardException.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.rewardService.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customers/")
@Slf4j
public class CustomerController {

	@Autowired
	CustomerServiceImpl customerService;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	/**
	 * @param id
	 * Get customer by Id
	 * http://localhost:8080/api/customers/1
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id)
	{
		try 
		{
			CustomerDTO customer= customerService.getCustomerById(id);
			logger.info("Requested Customer is : "+ id);
			return new ResponseEntity<>(customer,HttpStatus.OK);
		}
		catch(CustomerNotFoundException customerException) 
		{
			logger.error(customerException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,customerException.getMessage());
		}
	}

}
