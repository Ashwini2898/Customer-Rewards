package com.rewardsystem.rewardmanager.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rewardsystem.rewardmanager.Entity.Customer;
import com.rewardsystem.rewardmanager.Entity.Transaction;
import com.rewardsystem.rewardmanager.Exception.InvalidTransactionException;
import com.rewardsystem.rewardmanager.Repository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.Repository.TransactionRepositoryDao;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

	@Mock
	private TransactionRepositoryDao transactionRepository;

	@Mock
	private CustomerRepositoryDao customerDao;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	private Customer customer;
	private Transaction transaction;

	@BeforeEach
	void setUp() {
		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustName("John");
		customer.setCustMobile("9876543210");
		customer.setRewardPoints(50);

		transaction = new Transaction();
		transaction.setTransactionId(101L);
		transaction.setAmountSpent(120.0);
		transaction.setCustomer(customer);
		transaction.setDate(LocalDateTime.now());
	}

	@Test
	void testAddTransaction() throws InvalidTransactionException {
		when(customerDao.findById(1L)).thenReturn(Optional.of(customer));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Transaction result = transactionService.createTransaction(1L, 120.0);

		assertNotNull(result);
		assertEquals(120.0, result.getAmountSpent());
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}

	@Test
	void testGetCustomerPoints_Success() throws InvalidTransactionException {
		when(customerDao.findById(1L)).thenReturn(Optional.of(customer));

		Integer points = transactionService.getCustomerPoints(1L);

		assertNotNull(points);
		assertEquals(50, points);
	} 
}
