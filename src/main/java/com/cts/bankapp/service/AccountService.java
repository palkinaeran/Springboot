package com.cts.bankapp.service;

import java.util.List;

import com.cts.bankapp.dto.AccountDto;


public interface AccountService {
	
	AccountDto createAccount(AccountDto accountDto);
	
	AccountDto getAccountById(Long id);
	
	AccountDto deposit(long id, double amount);
	AccountDto withdraw(long id, double amount);
	
	List<AccountDto> getAllAccounts();
	
	void deleteAccount(long id);

}
