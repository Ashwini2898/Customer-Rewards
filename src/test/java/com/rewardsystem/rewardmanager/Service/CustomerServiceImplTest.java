package com.rewardsystem.rewardmanager.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.rewardsystem.rewardmanager.dto.CustomerDTO;
import com.rewardsystem.rewardmanager.mapper.CustomerMapper;
import com.rewardsystem.rewardmanager.rewardEntity.Customer;
import com.rewardsystem.rewardmanager.rewardEntity.Transaction;
import com.rewardsystem.rewardmanager.rewardException.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardRepository.CustomerRepositoryDao;
import com.rewardsystem.rewardmanager.rewardRepository.TransactionRepositoryDao;
import com.rewardsystem.rewardmanager.rewardService.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	CustomerRepositoryDao customerDao;

	@Mock
	TransactionRepositoryDao transactionRepository;

	@Mock
	private CustomerMapper customerMapper;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private Customer customer;
	private CustomerDTO customerDTO;

	@BeforeEach
	void setUp() {
		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustName("John Doe");

		customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setCustName("John Doe");
	}

	@Test
	void verifyGetCustomerById_Success() throws CustomerNotFoundException {

		when(customerDao.findById(1L)).thenReturn(Optional.of(customer));
		when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

		CustomerDTO result = customerService.getCustomerById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getCustomerId());
		assertEquals("John Doe", result.getCustName());
		verify(customerDao, times(1)).findById(1L);
		verify(customerMapper, times(1)).toDTO(customer);
	}

	@Test
	void testGetCustomerById_NotFound() {

		when(customerDao.findById(1L)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));
		verify(customerDao, times(1)).findById(1L);
		verify(customerMapper, never()).toDTO(any());
	}
}
