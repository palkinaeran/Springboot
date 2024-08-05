package com.cts.bankapp.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.cts.bankapp.dto.AccountDto;
import com.cts.bankapp.entity.Account;
import com.cts.bankapp.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class TestServiceImpl {

	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private AccountServiceImpl accountServiceImpl;
	

    private Account account;
	private AccountDto accountDto;
	
	
	@BeforeEach 
	void setup() {
		MockitoAnnotations.openMocks(this);
		
		accountDto = new AccountDto();
		accountDto.setId(1L);
		accountDto.setAccountHolderName("John Doe");
		accountDto.setBalance(123456.0);
		
		account = new Account();
		account.setId(1L);
		account.setAccountHolderName("John Doe");
		account.setBalance(123456.0);
		
		

	}
	

	@Test
	public void testCreateAccount() {
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		
		AccountDto createdAccount = accountServiceImpl.createAccount(accountDto);
	
		assertEquals(accountDto.getId(), createdAccount.getId());
		assertEquals(accountDto.getAccountHolderName(), createdAccount.getAccountHolderName());
		assertEquals(accountDto.getBalance(), createdAccount.getBalance());
		
		
	}
	
	@Test
	public void testGetAllAccounts() {
		
		when(accountRepository.findAll()).thenReturn(List.of(account));
		
		List<AccountDto> result= accountServiceImpl.getAllAccounts();
		
		assertEquals(1, result.size());
		assertEquals(accountDto.getId(),result.get(0).getId());
		assertEquals(accountDto.getAccountHolderName(),result.get(0).getAccountHolderName());
		assertEquals(123456.0,result.get(0).getBalance());
		
	}

	
	@Test
	public void testGetAccountById() {
		//mocking the behaviour of accountRepository.findById(1L);
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		
		AccountDto foundAccount = accountServiceImpl.getAccountById(account.getId());
		
		assertEquals(accountDto.getId(),foundAccount.getId());
		assertEquals(accountDto.getAccountHolderName(),foundAccount.getAccountHolderName());
		assertEquals(accountDto.getBalance(),foundAccount.getBalance());
	}
	
	@Test
	public void testGetAccountByIdNotFound() {
		
		when(accountRepository.findById(1L)).thenReturn(Optional.empty()); //no values provided
		
		assertThrows(RuntimeException.class, ()->accountServiceImpl.getAccountById(1L));
		
	}
	
	@Test
	public void testDeposit() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		
		AccountDto result = accountServiceImpl.deposit(1L, 500.0);
		
		assertEquals(accountDto.getId(),result.getId());
		assertEquals(accountDto.getAccountHolderName(),result.getAccountHolderName());
		assertEquals(123956.0,result.getBalance());
		
	}
	
	@Test
	public void testDepositAccountNotFound() {
		
		when(accountRepository.findById(1L)).thenReturn(Optional.empty()); //no values provided
		
		assertThrows(RuntimeException.class, ()->accountServiceImpl.withdraw(1L,500.0));
		
	}
	
	
	@Test
	public void testWithdraw() {
		
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		
		AccountDto result = accountServiceImpl.withdraw(1L, 500.0);
		
		assertEquals(accountDto.getId(),result.getId());
		assertEquals(accountDto.getAccountHolderName(),result.getAccountHolderName());
		assertEquals(122956.0,result.getBalance());

	}
	@Test
	public void testWithdrawAccountNotFound() {
		
		when(accountRepository.findById(1L)).thenReturn(Optional.empty()); //no values provided
		
		assertThrows(RuntimeException.class, ()->accountServiceImpl.deposit(1L,500.0));
		
	}

	@Test
	public void testWithdrawInsufficientAmount() {
		
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		assertThrows(RuntimeException.class, ()->accountServiceImpl.deposit(1L,500.0));
	}
	
	@Test
	public void testDeleteAccount() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		 
		 doNothing().when(accountRepository).deleteById(1L);
		 
		 accountServiceImpl.deleteAccount(1L);
		verify(accountRepository).deleteById(1L);
		
	}
	
	@Test
	public void testDeleteIdIsPresentOrNot() {
		
		when(accountRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, ()->accountServiceImpl.deleteAccount(1L));
	}

}
