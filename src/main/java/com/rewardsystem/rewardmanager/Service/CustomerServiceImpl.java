package com.rewardsystem.rewardmanager.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Exception.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;

@Service
public class CustomerServiceImpl {
	
	@Autowired
	CustomerRepositoryDao customerDao;
	
	//calling addCustomer method of DAO layer
		/**
		 * add customer
		 */
	
	public Customer addCustomer(Customer customer) throws CustomerNotFoundException 
	{
		try 
		{
			customerDao.save(customer);
			return customer;
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new CustomerNotFoundException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new CustomerNotFoundException(exception.getMessage());
		}

	}



	//calling getCustomerById method of DAO layer
	/**
	 * getCustomerById
	 */
	
	public Customer getCustomerById(Integer customerId) throws CustomerNotFoundException 
	{
		try
		{            
			Optional<Customer> optional=  customerDao.findById(customerId);
			if(optional.isPresent())
			{
				return optional.get();
			}
			else
			{
				return null;
			}
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new CustomerNotFoundException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new CustomerNotFoundException(exception.getMessage());
		}
	}


	//calling deleteCustomer method of DAO layer
	/**
	 * deleteCustomer
	 */
	
	public Integer deleteCustomer(Integer customerId) throws CustomerNotFoundException 
	{
		try 
		{            
			customerDao.deleteById(customerId);
			return customerId;
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new CustomerNotFoundException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new CustomerNotFoundException(exception.getMessage());
		}
	}



	//calling getAllCustomer method of DAO layer
	/**
	 * getAllCustomer
	 */
	
	public List<Customer> getAllCustomer() throws CustomerNotFoundException
	{
		try 
		{            
			List<Customer> customerList= customerDao.findAll();
			return customerList;
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new CustomerNotFoundException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new CustomerNotFoundException(exception.getMessage());
		}
	}



	//calling updateCustomer method of DAO layer
	/**
	 * updateCustomer
	 */
	
	public Customer updateCustomer(Customer customer) throws CustomerNotFoundException 
	{
		try 
		{            
			Customer updatedCustomer= customerDao.save(customer);    
			return updatedCustomer;
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new CustomerNotFoundException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new CustomerNotFoundException(exception.getMessage());
		}
	}
	
	

}
