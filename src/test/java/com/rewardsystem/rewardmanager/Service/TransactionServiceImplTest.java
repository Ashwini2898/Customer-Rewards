package com.rewardsystem.rewardmanager.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.mapper.TransactionMapper;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardRepository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.rewardRepository.TransactionRepositoryDao;
import com.rewardsystem.rewardmanager.rewardService.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

	@Mock
	private CustomerRepositoryDao customerDao;

	@Mock
	private TransactionRepositoryDao transactionRepository;

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	private Customer customer;
	private Transaction transaction;
	private TransactionDTO transactionDTO;

	@BeforeEach
	void setUp() {

		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustName("John Doe");
		customer.setRewardPoints(50);
		customer.setTotalSpent(100.0);

		transaction = new Transaction();
		transaction.setTransactionId(10L);
		transaction.setCustomer(customer);
		transaction.setAmountSpent(120.0);
		transaction.setAwardedPoints(90);
		transaction.setDate(LocalDateTime.now());

		transactionDTO = new TransactionDTO();
		transactionDTO.setTransactionId(10L);
		transactionDTO.setCustomerId(1L);
		transactionDTO.setAmountSpent(120.0);
		transactionDTO.setAwardedPoints(90);
	}

	@Test
	void verifyCreateTransaction_Success() throws InvalidTransactionException {
		when(customerDao.findById(1L)).thenReturn(Optional.of(customer));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
		when(customerDao.save(any(Customer.class))).thenReturn(customer);
		when(transactionMapper.toDTO(any(Transaction.class))).thenReturn(transactionDTO);

		TransactionDTO result = transactionService.createTransaction(1L, 120.0);

		assertNotNull(result);
		assertEquals(10L, result.getTransactionId());
		assertEquals(1L, result.getCustomerId());

		verify(customerDao).findById(1L);
		verify(transactionRepository).save(any(Transaction.class));
		verify(customerDao).save(any(Customer.class));
		verify(transactionMapper).toDTO(any(Transaction.class));
	}

	@Test
	void verifyCreateTransaction_CustomerNotFound() {
		when(customerDao.findById(1L)).thenReturn(Optional.empty());

		assertThrows(InvalidTransactionException.class, () -> 
		transactionService.createTransaction(1L, 120.0)
				);

		verify(customerDao).findById(1L);
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void verifyGetCustomerPoints_Success() throws InvalidTransactionException {
		when(customerDao.findById(1L)).thenReturn(Optional.of(customer));

		Integer points = transactionService.getCustomerPoints(1L);

		assertEquals(50, points);
		verify(customerDao).findById(1L);
	}

	@Test
	void verifyGetCustomerPoints_CustomerNotFound() {
		when(customerDao.findById(1L)).thenReturn(Optional.empty());

		assertThrows(InvalidTransactionException.class, () -> 
		transactionService.getCustomerPoints(1L)
				);
	}

	@Test
	void verifyGetAllTransactions_Success() throws InvalidTransactionException {
		when(transactionRepository.findAll()).thenReturn(List.of(transaction));
		when(transactionMapper.toDTO(anyList())).thenReturn(List.of(transactionDTO));

		List<TransactionDTO> result = transactionService.getAllTransactions();

		assertEquals(1, result.size());
		verify(transactionRepository).findAll();
		verify(transactionMapper).toDTO(anyList());
	}

	@Test
	void verifyGetCustomerTransactions_Success() throws InvalidTransactionException {
		LocalDateTime from = LocalDateTime.now().minusMonths(3);
		LocalDateTime to = LocalDateTime.now();

		when(transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(1L, from, to))
		.thenReturn(Arrays.asList(transaction));
		when(transactionMapper.toDTO(anyList())).thenReturn(List.of(transactionDTO));

		List<TransactionDTO> result = transactionService.getCustomerTransactions(1L, from, to);

		assertEquals(1, result.size());
		verify(transactionRepository).findAllByCustomer_CustomerIdAndDateBetween(1L, from, to);
		verify(transactionMapper).toDTO(anyList());
	}

	@Test
	void verifyGetAllTransactionsByCustomerId_Success() throws InvalidTransactionException {
		when(transactionRepository.findAllByCustomer_CustomerId(1L))
		.thenReturn(Collections.singletonList(transaction));
		when(transactionMapper.toDTO(anyList())).thenReturn(List.of(transactionDTO));

		List<TransactionDTO> result = transactionService.getAllTransactionsByCustomerId(1L);

		assertEquals(1, result.size());
		verify(transactionRepository).findAllByCustomer_CustomerId(1L);
		verify(transactionMapper).toDTO(anyList());
	}
}

