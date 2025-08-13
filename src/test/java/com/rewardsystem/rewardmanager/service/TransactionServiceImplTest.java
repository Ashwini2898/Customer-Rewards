package com.rewardsystem.rewardmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import com.rewardsystem.rewardmanager.rewardRepository.TransactionRepositoryDao;
import com.rewardsystem.rewardmanager.rewardService.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

	@Mock
	private TransactionRepositoryDao transactionRepository;

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	private Customer customer;
	private Transaction transaction1;
	private Transaction transaction2;
	private TransactionSummaryDTO transactionSummaryDTO;
	private TransactionDTO dto;

	@BeforeEach
	void setUp() {

		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustName("John Doe");
		customer.setRewardPoints(50);
		customer.setTotalSpent(100.0);

		transaction1 = new Transaction();
		transaction1.setTransactionId(10L);
		transaction1.setCustomer(customer);
		transaction1.setAmountSpent(120.0);
		transaction1.setAwardedPoints(90.0);
		transaction1.setDate(LocalDateTime.of(2025, 8, 12, 10, 30, 0));

		transaction2 = new Transaction();
		transaction2.setTransactionId(5L);
		transaction2.setCustomer(customer);
		transaction2.setAmountSpent(100.0);
		transaction2.setAwardedPoints(50.0);
		transaction2.setDate(LocalDateTime.of(2025, 8, 12, 10, 30, 0));

		transactionSummaryDTO = new TransactionSummaryDTO();
		transactionSummaryDTO.setTransactionId(10L);
		transactionSummaryDTO.setAmountSpent(120.0);
		transactionSummaryDTO.setAwardedPoints(90.0);
		transactionSummaryDTO.setDate(transaction1.getDate());
		
		 dto = new TransactionDTO(
				1L, "John Doe", 140.0,
				List.of(
						new TransactionSummaryDTO(
								transaction1.getTransactionId(),
								transaction1.getAmountSpent(),
								transaction1.getDate(),
								transaction1.getAwardedPoints()
								),
						new TransactionSummaryDTO(
								transaction2.getTransactionId(),
								transaction2.getAmountSpent(),
								transaction2.getDate(),
								transaction2.getAwardedPoints()
								)
						)
				);
	}

	@Test
	void shouldReturnAllTransactions_whenGetAllTransactionsIsCalled() throws InvalidTransactionException {
		List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

		when(transactionRepository.findAll()).thenReturn(transactions);
		when(transactionMapper.toDTO(transactions)).thenReturn(List.of(dto));

		List<TransactionDTO> result = transactionService.getAllTransactions();

		assertEquals(1, result.size(), "Expected exactly one customer DTO");
		TransactionDTO returnedDto = result.get(0);

		assertEquals(1L, returnedDto.getCustomerId());
		assertEquals("John Doe", returnedDto.getCustomerName());
		assertEquals(140.0, returnedDto.getTotalPoints());

		assertEquals(2, returnedDto.getTransactions().size());
		assertEquals(transaction1.getTransactionId(), returnedDto.getTransactions().get(0).getTransactionId());
		assertEquals(transaction1.getAmountSpent(), returnedDto.getTransactions().get(0).getAmountSpent());
		assertEquals(transaction1.getAwardedPoints(), returnedDto.getTransactions().get(0).getAwardedPoints());

		assertEquals(transaction2.getTransactionId(), returnedDto.getTransactions().get(1).getTransactionId());
		assertEquals(transaction2.getAmountSpent(), returnedDto.getTransactions().get(1).getAmountSpent());
		assertEquals(transaction2.getAwardedPoints(), returnedDto.getTransactions().get(1).getAwardedPoints());

		verify(transactionRepository, times(1)).findAll();
		verify(transactionMapper, times(1)).toDTO(transactions);
	}

	@Test
	void shouldReturnAllCustomerTransaction_whenGetTransactionByCustomerIDIsCalled() throws InvalidTransactionException {
		LocalDateTime from = LocalDateTime.of(2025, 8, 12, 10, 30, 0).minusMonths(3);
		LocalDateTime to = LocalDateTime.of(2025, 8, 12, 10, 30, 0);

		when(transactionRepository.findAllByCustomer_CustomerIdAndDateBetween(1L, from, to))
		.thenReturn(Arrays.asList(transaction1));
		when(transactionMapper.toSummaryDTO(anyList()))
		.thenReturn(List.of(transactionSummaryDTO));

		List<TransactionSummaryDTO> result = transactionService.getCustomerTransactions(1L, from, to);

		assertEquals(1, result.size());
		TransactionSummaryDTO summary = result.get(0);

		assertEquals(10L, summary.getTransactionId());
		assertEquals(120.0, summary.getAmountSpent());
		assertEquals(90.0, summary.getAwardedPoints());
		assertEquals(transaction1.getDate(), summary.getDate());

		verify(transactionRepository, times(1))
		.findAllByCustomer_CustomerIdAndDateBetween(1L, from, to);
		verify(transactionMapper, times(1)).toSummaryDTO(anyList());
	}

	@Test
	void shouldThrowException_whenRepositoryThrowsException() {
		when(transactionRepository.findAll()).thenThrow(new InvalidTransactionException("DB failure"));

		assertThrows(InvalidTransactionException.class, () -> transactionService.getAllTransactions());

		verify(transactionRepository, times(1)).findAll();
		verify(transactionMapper, times(0)).toDTO(any());
	}

	@Test
	void shouldReturnEmptyList_whenNoTransactionsFound() throws InvalidTransactionException {
		when(transactionRepository.findAll()).thenReturn(Collections.emptyList());
		when(transactionMapper.toDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

		List<TransactionDTO> result = transactionService.getAllTransactions();

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(transactionRepository, times(1)).findAll();
		verify(transactionMapper, times(1)).toDTO(Collections.emptyList());
	}
	
}

