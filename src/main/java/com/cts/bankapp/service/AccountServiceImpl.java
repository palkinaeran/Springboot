package com.cts.bankapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.bankapp.dto.AccountDto;
import com.cts.bankapp.entity.Account;
import com.cts.bankapp.mapper.AccountMapper;
import com.cts.bankapp.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}



	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount= accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
		
	}



	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doesnot exists"));
		return AccountMapper.mapToAccountDto(account);
	}



	@Override
	public AccountDto deposit(long id, double amount) {
		Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doesnot exists"));
		
		double total = account.getBalance()+amount;
		account.setBalance(total);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);

	}



	@Override
	public AccountDto withdraw(long id, double amount) {
Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doesnot exists"));
		
		if(account.getBalance()< amount) {
			throw new RuntimeException("Insufficient Amount");
		}
		double total= account.getBalance()-amount;
		account.setBalance(total);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}



	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map((account)-> AccountMapper.mapToAccountDto(account))
				.collect(Collectors.toList());
		
		}



	@Override
	public void deleteAccount(long id) {
		accountRepository
		        .findById(id)
				.orElseThrow(()-> new RuntimeException("Account doesnot exists"));
		accountRepository.deleteById(id);
	}

}
