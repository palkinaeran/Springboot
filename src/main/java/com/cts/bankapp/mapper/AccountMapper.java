package com.cts.bankapp.mapper;

import com.cts.bankapp.dto.AccountDto;
import com.cts.bankapp.entity.Account;

public class AccountMapper {
	
	public static Account mapToAccount(AccountDto accountDto) {  //changing accountDto entity to jpa entity 
		Account account = new Account(
				accountDto.getId(),
				accountDto.getAccountHolderName(),
				accountDto.getBalance()
				);
		return account;
	}
	public static AccountDto mapToAccountDto(Account account) { //changing jpa entity to accountdto
		AccountDto accountDto= new AccountDto(
				account.getId(),
				account.getAccountHolderName(),
				account.getBalance()
				);
		return accountDto;
	}

}
