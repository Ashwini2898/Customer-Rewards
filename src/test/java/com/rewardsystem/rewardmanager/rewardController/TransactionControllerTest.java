package com.rewardsystem.rewardmanager.rewardController;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.rewardException.InvalidTransactionException;
import com.rewardsystem.rewardmanager.rewardService.TransactionServiceImpl;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionServiceImpl transactionService;

	@Test
	void createTransaction_success() throws Exception {
		TransactionDTO dto = new TransactionDTO();
		dto.setTransactionId(1L);
		dto.setAmountSpent(120.0);
		dto.setAwardedPoints(90);

		when(transactionService.createTransaction(eq(1L), eq(120.0)))
		.thenReturn(dto);

		mockMvc.perform(post("/api/transactions/new-transaction/")
				.param("customerId", "1")
				.param("amount", "120"))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.transactionId").value(1))
		.andExpect(jsonPath("$.amountSpent").value(120.0))
		.andExpect(jsonPath("$.awardedPoints").value(90));
	}

	@Test
	void createTransaction_invalid() throws Exception {
		when(transactionService.createTransaction(anyLong(), anyDouble()))
		.thenThrow(new InvalidTransactionException("Invalid transaction"));

		mockMvc.perform(post("/api/transactions/new-transaction/")
				.param("customerId", "1")
				.param("amount", "120"))
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("Invalid transaction"));
	}

	@Test
	void getCustomerPoints_success() throws Exception {
		when(transactionService.getCustomerPoints(1L)).thenReturn(150);

		mockMvc.perform(get("/api/transactions/customers/1/points/"))
		.andExpect(status().isOk())
		.andExpect(content().string("total poits are: 150"));
	}

	@Test
	void getCustomerPoints_invalid() throws Exception {
		when(transactionService.getCustomerPoints(1L))
		.thenThrow(new InvalidTransactionException("Customer not found"));

		mockMvc.perform(get("/api/transactions/customers/1/points/"))
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("Customer not found"));
	}

	@Test
	void getAllTransactions_success() throws Exception {
		TransactionDTO dto = new TransactionDTO();
		dto.setTransactionId(1L);
		dto.setAmountSpent(50.0);

		when(transactionService.getAllTransactions())
		.thenReturn(List.of(dto));

		mockMvc.perform(get("/api/transactions/getAllTransactions"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].transactionId").value(1))
		.andExpect(jsonPath("$[0].amountSpent").value(50.0));
	}

	@Test
	void getCustomerTransactions_success() throws Exception {
		TransactionSummaryDTO dto = new TransactionSummaryDTO();
		dto.setTransactionId(1L);

		when(transactionService.getCustomerTransactions(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(List.of(dto));

		mockMvc.perform(get("/api/transactions/customers/1/getTransactionByCustomerID")
				.param("fromDate", "01-01-2025")
				.param("toDate", "05-01-2025"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].transactionId").value(1));
	}

	@Test
	void getCustomerAllTransactions_success() throws Exception {
		TransactionSummaryDTO dto = new TransactionSummaryDTO();
		dto.setTransactionId(1L);

		when(transactionService.getAllTransactionsByCustomerId(1L))
		.thenReturn(List.of(dto));

		mockMvc.perform(get("/api/transactions/customers/1/getAllTransactionForCustomer"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].transactionId").value(1));
	}

}
