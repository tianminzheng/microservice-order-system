package com.tianyalan.account.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.account.model.Account;
import com.tianyalan.account.services.AccountService;

@RestController
@RequestMapping(value = "v1/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public Account getAccount(@PathVariable("accountId") Long accountId) {
		
		logger.info("Get account by id: {} ", accountId);
		
		/* 使用Mock数据
		Account account = new Account();
		account.setContactEmail("tianyalan@email.com");
		account.setContactName("tianyalan");
		account.setContactPhone("11111111");
		account.setName("tianyalan");
		account.setId(1L);

		return account;
		*/
		
		 Account account = accountService.getAccountById(accountId);
		 return account;
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public void updateAccount(@RequestBody Account account) {
		accountService.updateAccount(account);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void saveAccount(@RequestBody Account account) {
		accountService.updateAccount(account);
	}

	@RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAccount(@PathVariable("accountId") Long accountId) {
		Account account = new Account();
		account.setId(accountId);

		accountService.deleteAccount(account);
	}
}
