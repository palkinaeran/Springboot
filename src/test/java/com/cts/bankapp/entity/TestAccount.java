package com.cts.bankapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAccount {
	
	Account account = new Account();

	@Test
	public void testDefaultConstructor() {
		assertNotNull(account);
	}
	
	@Test
	public void testParameterizedConstructor() {
		account = new Account(1L, "John Doe", 123456.0);
		assertEquals(1L, account.getId());
		assertEquals("John Doe", account.getAccountHolderName());
		assertEquals(123456.0, account.getBalance());
	}
	
	@Test
	public void testGettersAndSetters() {
		account.setAccountHolderName("John Doe");
		account.setBalance(123456.0);
		account.setId(1L);
		
		assertEquals(1L, account.getId());
		assertEquals("John Doe", account.getAccountHolderName());
		assertEquals(123456.0, account.getBalance());
		
		
	}

}
