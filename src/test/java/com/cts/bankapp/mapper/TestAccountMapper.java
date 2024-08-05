package com.cts.bankapp.mapper;

import com.cts.bankapp.dto.*;
import com.cts.bankapp.entity.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAccountMapper {

	@Test
	public void testMapToAccount() {
		AccountDto accountDto = new AccountDto();
		accountDto.setId(1L);
		accountDto.setAccountHolderName("John Doe");
		accountDto.setBalance(12345.0);
		
		//call the static method
		Account account = AccountMapper.mapToAccount(accountDto);
		
		assertEquals(accountDto.getAccountHolderName(),account.getAccountHolderName());
		assertEquals(accountDto.getBalance(),account.getBalance());
		assertEquals(accountDto.getId(),account.getId());
		
	}
	@Test
	public void testMapToAccountDto() {
		Account account = new Account();
		account.setId(1L);
		account.setAccountHolderName("John Doe");
		account.setBalance(12345.0);
		
		//call the static method
		AccountDto accountDto = AccountMapper.mapToAccountDto(account);
		
		assertEquals(account.getAccountHolderName(),accountDto.getAccountHolderName());
		assertEquals(account.getBalance(),accountDto.getBalance());
		assertEquals(account.getId(),accountDto.getId());
		
	}

}
