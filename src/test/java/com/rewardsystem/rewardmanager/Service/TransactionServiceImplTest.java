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
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
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
	private TransactionSummaryDTO transactionSummaryDTO;

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
		
		transactionSummaryDTO = new TransactionSummaryDTO();
		transactionSummaryDTO.setTransactionId(10L);
		transactionSummaryDTO.setAmountSpent(120.0);
		transactionSummaryDTO.setAwardedPoints(90);
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
		when(transactionMapper.toSummaryDTO(anyList())).thenReturn(List.of(transactionSummaryDTO));

		List<TransactionSummaryDTO> result = transactionService.getCustomerTransactions(1L, from, to);

		assertEquals(1, result.size());
		verify(transactionRepository).findAllByCustomer_CustomerIdAndDateBetween(1L, from, to);
		verify(transactionMapper).toSummaryDTO(anyList());
	}


}

