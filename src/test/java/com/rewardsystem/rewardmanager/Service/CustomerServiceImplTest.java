package com.rewardsystem.rewardmanager.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Entity.Transaction;
import com.rewardsystem.rewardmanager.Exception.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.Exception.InvalidTransactionException;
import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.Repository.TransactionRepositoryDao;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	CustomerRepositoryDao customerDao;

	@Mock
	TransactionRepositoryDao transactionRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private Customer mockCustomer;
	private Transaction transaction;

	@BeforeEach
	void setUp() {
		mockCustomer = new Customer();
		mockCustomer.setCustomerId(1L);
		mockCustomer.setCustName("John");
		mockCustomer.setCustMobile("9876543210");
		mockCustomer.setTotalSpent(100.0);
		mockCustomer.setRewardPoints(50);
	}

	@Test
	void verifyAddCustomer() throws CustomerNotFoundException {
		when(customerDao.save(any(Customer.class))).thenReturn(mockCustomer);

		Customer result = customerService.addCustomer(mockCustomer);
		assertNotNull(result);
		assertEquals(mockCustomer, result);
	}

	@Test
	void verifyGetCustomerById() throws CustomerNotFoundException {

		when(customerDao.findById(1L)).thenReturn(Optional.of(mockCustomer));
		Customer result = customerService.getCustomerById(1L);
		assertNotNull(result);
		assertEquals("John", result.getCustName());
		// assertEquals("9876543210", result.getCustMobile());

	}

	@Test
	void verifyGetAllCustomers() throws CustomerNotFoundException {
		List<Customer> customers = List.of(mockCustomer);
		when(customerDao.findAll()).thenReturn(customers);

		List<Customer> result = customerService.getAllCustomer();
		assertEquals(1, result.size());
	}

	@Test
	void verifyGetTransactionsForMonth() throws InvalidTransactionException {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustName("John");
		customer.setCustMobile("9876543210");
		customer.setTotalSpent(100.0);
		customer.setRewardPoints(50);

		Transaction t1 = new Transaction();
		t1.setTransactionId(1L);
		t1.setAmountSpent(100.0);
		t1.setCustomer(customer);
		t1.setDate(LocalDateTime.of(2025, 7, 10, 12, 0));
		t1.setAwardedPoints(50);

		Transaction t2 = new Transaction();
		t2.setTransactionId(2L);
		t2.setAmountSpent(150.0);
		t2.setCustomer(customer);
		t2.setDate(LocalDateTime.of(2025, 7, 20, 15, 30));
		t2.setAwardedPoints(150);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(t1);
		transactions.add(t2);

		when(transactionRepository.findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(transactions);

		List<Transaction> result = customerService.getTransactionsForMonth(2025, 7);

		assertEquals(2, result.size());
		assertEquals(t1,result.get(0));
		assertEquals(t2,result.get(1));
	}

	@Test
	void getCustomerTransactionsForLastOneMonth() throws InvalidTransactionException {
		Transaction t1 = new Transaction();
		t1.setTransactionId(1L);
		t1.setAmountSpent(100.0);
		t1.setCustomer(mockCustomer);
		t1.setDate(LocalDateTime.of(2025, 7, 10, 12, 0));
		t1.setAwardedPoints(50);

		Transaction t2 = new Transaction();
		t2.setTransactionId(2L);
		t2.setAmountSpent(150.0);
		t2.setCustomer(mockCustomer);
		t2.setDate(LocalDateTime.of(2025, 6, 20, 15, 30));
		t2.setAwardedPoints(150);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(t1);
		transactions.add(t2);
		
		when(transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(any(Long.class),
				any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(transactions);
		List<Transaction> result = customerService.getCustomerTransactionsForLastOneMonth(1L);
		assertEquals(2, result.size());
	}
}
