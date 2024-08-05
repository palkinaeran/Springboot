package com.cts.bankapp.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAccountDto {
	
	AccountDto accountDto = new AccountDto();
	
	@Test
	public void testDefaultConstructor() {
		assertNotNull(accountDto);
	}
	
	@Test
	public void testParameterizedConstructor() {
		AccountDto accountDto = new AccountDto(1L,"Jane Doe",12345.0);
		
		assertEquals("Jane Doe",accountDto.getAccountHolderName());
		assertEquals(12345.0,accountDto.getBalance());
		assertEquals(1L, accountDto.getId());
	}

	@Test
	public void testGettersAndSetters() {
		
		accountDto.setAccountHolderName("Jane Doe");
		accountDto.setBalance(12345.0);
		accountDto.setId(1L);
		
		assertEquals("Jane Doe",accountDto.getAccountHolderName());
		assertEquals(12345.0,accountDto.getBalance());
		assertEquals(1L, accountDto.getId());
	
		
	}

}
