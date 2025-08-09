package com.rewardsystem.rewardmanager.rewardController;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rewardsystem.rewardmanager.dto.CustomerDTO;
import com.rewardsystem.rewardmanager.rewardException.CustomerNotFoundException;
import com.rewardsystem.rewardmanager.rewardService.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
class customerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerServiceImpl customerService; // mock the service dependency

	@Test
	void getCustomerById_Success() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setCustomerId(1L);
		customer.setCustName("John Doe");

		when(customerService.getCustomerById(1L)).thenReturn(customer);

		mockMvc.perform(get("/api/customers/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerId").value(1L))
		.andExpect(jsonPath("$.custName").value("John Doe"));
	}

	@Test
	void getCustomerById_NotFound() throws Exception {
		when(customerService.getCustomerById(99L))
		.thenThrow(new CustomerNotFoundException("Customer not found with ID: 99"));

		mockMvc.perform(get("/api/customers/99")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("Customer not found with ID: 99"));
	}
}
