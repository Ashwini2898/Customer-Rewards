package com.rewardsystem.rewardmanager.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Exception.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.Service.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller to handle customer-related endpoints.
 */
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
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id)
	{
		try 
		{
			Customer customer= customerService.getCustomerById(id);
			logger.info("Customer added"+ customer);
			return new ResponseEntity<>(customer,HttpStatus.OK);
		}
		catch(CustomerNotFoundException customerException) 
		{
			logger.error(customerException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,customerException.getMessage());
		}
	}

	/**
	 * Get all customer
	 * http://localhost:8080/api/customers/
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<List<Customer>> getAllCustomer()
	{
		try 
		{
			List<Customer> customerList = customerService.getAllCustomer();
			logger.info("Returning all customer details");
			return new ResponseEntity<>(customerList,HttpStatus.OK);
		}
		catch(CustomerNotFoundException customerException) 
		{
			logger.error(customerException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,customerException.getMessage());
		}
	}

	/**
	 * @param customer
	 * Add Customer
	 * http://localhost:8080/api/customers/
	 * @return
	 */
	@PostMapping("/")
	public String addCustomer( @RequestBody Customer customer) 
	{
		try 
		{
			Customer newCustomer= customerService.addCustomer(customer);

			logger.info("customer:"+newCustomer.getCustName()+" added to database");
			return "customer:"+newCustomer.getCustName()+" added to database";

		}
		catch(CustomerNotFoundException customerException)
		{
			logger.error(customerException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,customerException.getMessage());
		}
	}

	/**
	 * @param id
	 * Delete Customer
	 * http://localhost:8080/api/customer/1
	 * @return
	 */
	@DeleteMapping("/{id}")
	public String deleteCustomer(@PathVariable Long id) 
	{
		try
		{
			Long status= customerService.deleteCustomer(id);
			if(status ==1) 
			{
				logger.info("customer: "+id+" deleted from database");
				return "customer: "+id+" deleted from database";
			}
			else
			{
				logger.debug("Unable to delete customer from database");
				return "Unable to delete customer from database";
			}
		}
		catch(CustomerNotFoundException customerException)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,customerException.getMessage());
		}
	}

	/**
	 * @param customerMaster
	 * Update Customer
	 * http://localhost:8080/api/customer/
	 * @return
	 */
	@PutMapping("/")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customerMaster)
	{
		try
		{
			Customer updatedCustomer= customerService.updateCustomer(customerMaster);
			logger.info("Product: "+ customerMaster.getCustomerId()+ " updated");
			return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
		}
		catch(CustomerNotFoundException customerException) 
		{
			logger.error(customerException.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,customerException.getMessage());
		}
	}
}
