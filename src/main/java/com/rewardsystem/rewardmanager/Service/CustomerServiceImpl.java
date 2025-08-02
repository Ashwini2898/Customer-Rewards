package com.rewardsystem.rewardmanager.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Entity.Transaction;
import com.rewardsystem.rewardmanager.Exception.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.Exception.InvalidTransactionException;
import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.Repository.TransactionRepositoryDao;

/**
 * 
 * This is a Service class which contains business logic for customer entity to perform operations
 *
 */
@Service
public class CustomerServiceImpl {

	@Autowired
	CustomerRepositoryDao customerDao;

	@Autowired
	TransactionRepositoryDao transactionRepository;

	/**
	 * calling addCustomer method of DAO layer
	 */
	public Customer addCustomer( Customer customer) throws CustomerNotFoundException 
	{
		try 
		{
			customerDao.save(customer);
			return customer;
		}
		catch(Exception exception) {
			throw new CustomerNotFoundException(exception.getMessage());
		}
	}

	/**
	 * calling getCustomerById method of DAO layer
	 */
	public Customer getCustomerById(Long customerId) throws CustomerNotFoundException 
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
				throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
			}
		}
		catch(Exception exception)
		{
			throw new CustomerNotFoundException(exception.getMessage());
		}
	}

	/**
	 * calling deleteCustomer method of DAO layer
	 */
	public Long deleteCustomer(Long customerId) throws CustomerNotFoundException 
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

	/**
	 * calling getAllCustomer method of DAO layer
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

	/**
	 * calling updateCustomer method of DAO layer
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

	/**
	 * calling getTransactionsForMonth method of DAO layer to get all transactions done in month
	 */
	public List<Transaction> getTransactionsForMonth(int year, int month)  throws InvalidTransactionException{

		try {
			LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
			LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth())
					.withHour(23).withMinute(59).withSecond(59);

			return transactionRepository.findAllByDateBetween(start, end);
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new InvalidTransactionException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * calling getTransactionsForLastThreeMonths method of DAO layer to get all transactions done in last three months
	 */
	public List<Transaction> getTransactionsForLastThreeMonths() throws InvalidTransactionException{
		try {
			LocalDateTime end = LocalDateTime.now();
			LocalDateTime start = end.minusMonths(3).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

			return transactionRepository.findAllByDateBetween(start, end);
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new InvalidTransactionException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * calling getTransactionsForLastThreeMonths method of DAO layer to get all transactions done in last three months by Customer ID
	 */
	public List<Transaction> getCustomerTransactionsForLastThreeMonths(Long customerId) throws InvalidTransactionException{
		try {
			LocalDateTime end = LocalDateTime.now();
			LocalDateTime start = end.minusMonths(3).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

			return transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(customerId, start, end);
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new InvalidTransactionException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}

	/**
	 * calling getTransactionsForLastThreeMonths method of DAO layer to get all transactions done in last one months by Customer ID
	 */
	public List<Transaction> getCustomerTransactionsForLastOneMonth(Long customerId) throws InvalidTransactionException{
		try {
			LocalDateTime end = LocalDateTime.now();
			LocalDateTime start = end.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

			return transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(customerId, start, end);
		}
		catch(DataAccessException dataAccessException) 
		{
			throw new InvalidTransactionException(dataAccessException.getMessage());
		}
		catch(Exception exception)
		{
			throw new InvalidTransactionException(exception.getMessage());
		}
	}
}
