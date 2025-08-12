package com.rewardsystem.rewardmanager.rewardController;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.rewardsystem.rewardmanager.dto.TransactionDTO;
import com.rewardsystem.rewardmanager.dto.TransactionSummaryDTO;
import com.rewardsystem.rewardmanager.rewardService.TransactionServiceImpl;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TransactionServiceImpl transactionService;

	@Test
	void shouldReturnAllTransactions_whenGetAllTransactionsIsCalled() throws Exception {
		TransactionDTO dto = new TransactionDTO(
				1L, "John Doe", 90.0,
				Map.of("2025-08", 90.0),
				List.of(new TransactionSummaryDTO(1L, 120.0, LocalDateTime.now(), 90.0))
				);

		when(transactionService.getAllTransactions()).thenReturn(List.of(dto));

		mockMvc.perform(get("/api/transactions/getAllTransactions")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].customerId").value(1))
		.andExpect(jsonPath("$[0].customerName").value("John Doe"))
		.andExpect(jsonPath("$[0].totalPoints").value(90.0))
		.andExpect(jsonPath("$[0].monthlyPoints['2025-08']").value(90.0))
		.andExpect(jsonPath("$[0].transactions[0].transactionId").value(1))
		.andExpect(jsonPath("$[0].transactions[0].amountSpent").value(120.0))
		.andExpect(jsonPath("$[0].transactions[0].awardedPoints").value(90.0))
		.andExpect(jsonPath("$[0].transactions[0].date").isNotEmpty());
	}

	@Test
	void shouldReturnAllCustomerTransaction_whenGetTransactionByCustomerIDIsCalled() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		TransactionSummaryDTO dto = new TransactionSummaryDTO(1L, 120.0, now, 90.0);

		when(transactionService.getCustomerTransactions(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(List.of(dto));

		mockMvc.perform(get("/api/transactions/1/getTransactionByCustomerID")
				.param("fromDate", "01-01-2025")
				.param("toDate", "05-01-2025"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].transactionId").value(1))
		.andExpect(jsonPath("$[0].amountSpent").value(120.0))
		.andExpect(jsonPath("$[0].awardedPoints").value(90.0))
		.andExpect(jsonPath("$[0].date").isNotEmpty());
	}
}
