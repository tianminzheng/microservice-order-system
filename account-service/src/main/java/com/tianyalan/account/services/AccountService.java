package com.tianyalan.account.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyalan.account.events.source.AccountChangeSource;
//import com.tianyalan.account.events.source.SimpleSourceBean;
import com.tianyalan.account.model.Account;
import com.tianyalan.account.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountChangeSource accountChangeSource;

    public Account getAccountById(Long accountId) {
        return accountRepository.findOne(accountId);
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
        
        accountChangeSource.publishAccountAddedEvent(account);
    }

    public void updateAccount(Account account){
    	accountRepository.save(account);
    	
    	accountChangeSource.publishAccountUpdatedEvent(account);
    }

    public void deleteAccount(Account account){
    	accountRepository.delete(account);
    	
    	accountChangeSource.publishAccountDeletedEvent(account);
    }
}
