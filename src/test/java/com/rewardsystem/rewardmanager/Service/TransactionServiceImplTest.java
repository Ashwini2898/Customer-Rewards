package com.rewardsystem.rewardmanager.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	private Transaction transaction1;
	private Transaction transaction2;
	private TransactionDTO transactionDTO;
	private TransactionSummaryDTO transactionSummaryDTO;

	@BeforeEach
	void setUp() {

		transaction1 = new Transaction();
		transaction1.setTransactionId(10L);
		transaction1.setCustomer(customer);
		transaction1.setAmountSpent(120.0);
		transaction1.setAwardedPoints(90.0);
		transaction1.setDate(LocalDateTime.now());

		transaction2 = new Transaction();
		transaction2.setTransactionId(5L);
		transaction2.setCustomer(customer);
		transaction2.setAmountSpent(100.0);
		transaction2.setAwardedPoints(50.0);
		transaction2.setDate(LocalDateTime.now());

		transactionSummaryDTO = new TransactionSummaryDTO();
		transactionSummaryDTO.setTransactionId(10L);
		transactionSummaryDTO.setAmountSpent(120.0);
		transactionSummaryDTO.setAwardedPoints(90.0);
	}

	@Test
	void verifyGetAllTransactions_Success() throws InvalidTransactionException {
		List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

		TransactionDTO dto = new TransactionDTO(
				1L, "John Doe", 90.0,
				Map.of("2025-08", 90.0),
				List.of(new TransactionSummaryDTO(1L, 120.0, transaction1.getDate(), 90.0))
				);

		Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
		Mockito.when(transactionMapper.toDTO(transactions)).thenReturn(List.of(dto));

		List<TransactionDTO> result = transactionService.getAllTransactions();

		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getCustomerName());
		Mockito.verify(transactionRepository, times(1)).findAll();
		Mockito.verify(transactionMapper, times(1)).toDTO(transactions);
	}

	@Test
	void verifyGetCustomerTransactions_Success() throws InvalidTransactionException {
		LocalDateTime from = LocalDateTime.now().minusMonths(3);
		LocalDateTime to = LocalDateTime.now();

		when(transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(1L, from, to))
		.thenReturn(Arrays.asList(transaction1));
		when(transactionMapper.toSummaryDTO(anyList())).thenReturn(List.of(transactionSummaryDTO));

		List<TransactionSummaryDTO> result = transactionService.getCustomerTransactions(1L, from, to);

		assertEquals(1, result.size());
		verify(transactionRepository).findAllByCustomer_CustomerIdAndDateBetween(1L, from, to);
		verify(transactionMapper).toSummaryDTO(anyList());
	}

}

