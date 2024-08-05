package com.cts.bankapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.bankapp.entity.Account;


@DataJpaTest
class TestAccountRepository {
	
	@Autowired
	AccountRepository accountRepository;
	
	Account account;

	//save method
	@Test
	void testSaveAccount() {
		
		account = new Account(1L,"John Doe", 123456.0);
		account = accountRepository.save(account);
		Optional<Account> foundAccount = accountRepository.findById(account.getId());
		
		assertThat(foundAccount).isPresent();
		
	}
	
	@Test
	public void testFindAccountById() {
		
		account = new Account(1L,"John Doe", 123456.0);
		account = accountRepository.save(account);
		Optional<Account> foundAccountById = accountRepository.findById(account.getId());
		
		assertThat(foundAccountById.get().getAccountHolderName()).isEqualTo("John Doe");
		assertThat(foundAccountById.get().getBalance()).isEqualTo(123456.0);

	}
	
	//delete method
	@Test
	public void testDeleteAccount() {
		account = new Account(1L,"John Doe", 123456.0);
		account = accountRepository.save(account);
		Optional<Account> foundAccount = accountRepository.findById(account.getId());
		
		assertThat(foundAccount).isPresent();
		accountRepository.delete(account);
		
		Optional<Account> deletedAccount = accountRepository.findById(account.getId());
		
		assertThat(deletedAccount).isNotPresent();
	}
	

}
