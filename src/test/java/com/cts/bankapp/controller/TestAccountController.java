package com.cts.bankapp.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.cts.bankapp.service.AccountService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cts.bankapp.dto.AccountDto;

import java.util.*;

@WebMvcTest(controllers=AccountController.class)
class TestAccountController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;


	@InjectMocks
	private AccountController accountController;
	

	@Test
	public void testCreateAccount() throws Exception{
		AccountDto accountDto = new AccountDto(1L, "John Doe", 123456.0);
		
		when(accountService.createAccount(any(AccountDto.class))).thenReturn(accountDto);
		
		mockMvc.perform(post("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				//.content("{\"id\":\"1L\",\"accountHolderName\":\"John Doe\",\"balance\":\"123456.0\"}"))
				.content(new ObjectMapper().writeValueAsString(accountDto)))
		        .andExpect(status().isCreated())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.id",is(accountDto.getId().intValue()))) //changed long id to int id
		        .andExpect(jsonPath("$.accountHolderName",is(accountDto.getAccountHolderName())))
		        .andExpect(jsonPath("$.balance",is(accountDto.getBalance())));
		
	}
	
	@Test
	public void testGetAccountById() throws Exception {
		AccountDto accountDto = new AccountDto(1L, "John Doe", 123456.0);
		
		when(accountService.getAccountById(1L)).thenReturn(accountDto);
		
		mockMvc.perform(get("/api/accounts/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.accountHolderName").value("John Doe"))
				.andExpect(jsonPath("$.balance").value(123456.0));
				
	}
	
	@Test
	public void testDeleteAccount() throws Exception {
		
         AccountDto accountDto = new AccountDto(1L, "John Doe", 123456.0);
		 when(accountService.getAccountById(1L)).thenReturn(accountDto);
		 
		 doNothing().when(accountService).deleteAccount(1L);
		 
		 mockMvc.perform(delete("/api/accounts/1"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testDeposit() throws Exception {
		long accountId=1L;
		double depositAmount=500.0;
		Map<String, Double> requestMap=new HashMap<>();
		requestMap.put("amount", depositAmount);
		
		AccountDto accounts = new AccountDto(1L, "John Doe", 123456.0);
		when(accountService.deposit(1L, 500.0)).thenReturn(accounts);
		
		mockMvc.perform(put("/api/accounts/1/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestMap)))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.id").value(1L)) //changed long id to int id
    			.andExpect(jsonPath("$.accountHolderName").value("John Doe"))
    			.andExpect(jsonPath("$.balance",is(accounts.getBalance())));
		
	}
	
	@Test
	public void testWithdraw() throws Exception {
		long accountId=1L;
		double depositAmount=500.0;
		Map<String, Double> requestMap=new HashMap<>();
		requestMap.put("amount", depositAmount);
		AccountDto accountDto = new AccountDto(1L, "John Doe", 123456.0);
		when(accountService.withdraw(1L, 500.0)).thenReturn(accountDto);
		
		mockMvc.perform(put("/api/accounts/"+accountId+"/withdraw")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestMap)))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    			.andExpect(jsonPath("$.id").value(1L)) //changed long id to int id
    			.andExpect(jsonPath("$.accountHolderName").value("John Doe"))
    			.andExpect(jsonPath("$.balance",is(accountDto.getBalance())));
			}
	
	@Test
	public void testGetAllAccounts() throws Exception {
		AccountDto accountDto1 = new AccountDto(1L, "John Doe", 123456.0);
		AccountDto accountDto2 = new AccountDto(2L, "Jane Doe", 123556.0);
		
		List<AccountDto> accounts = Arrays.asList(accountDto1, accountDto2);
		when(accountService.getAllAccounts()).thenReturn(accounts);
		
		mockMvc.perform(get("/api/accounts"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(1L))
		.andExpect(jsonPath("$[0].accountHolderName").value("John Doe"))
		.andExpect(jsonPath("$[0].balance").value(123456.0))
		.andExpect(jsonPath("$[1].id").value(2L))
		.andExpect(jsonPath("$[1].accountHolderName").value("Jane Doe"))
		.andExpect(jsonPath("$[1].balance").value(123556.0));
		
	}
}
