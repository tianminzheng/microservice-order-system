package com.tianyalan.orders.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import com.tianyalan.orders.model.Account;
import com.tianyalan.orders.repository.AccountRedisRepository;

@Component
public class AccountClient {

    private static final Logger logger = LoggerFactory.getLogger(AccountClient.class);
    
    @Autowired
    OAuth2RestTemplate restTemplate;
    
    @Autowired
    AccountRedisRepository accountRedisRepository;
    
    private Account getAccountFromCache(Long accountId) {
        try {
            return accountRedisRepository.findAccountById(accountId);
        }
        catch (Exception ex){
            return null;
        }
    }

    private void putAccountIntoCache(Account account) {
        try {
        	accountRedisRepository.saveAccount(account);
        }catch (Exception ex){
        }
    }
   
    public Account getAccount(Long accountId){
    	
    	logger.debug("Get account: {}", accountId);
    	
        Account account = getAccountFromCache(accountId);
        if (account != null){
            return account;
        }

        ResponseEntity<Account> restExchange =
                restTemplate.exchange(
                        "http://localhost:5555/api/account/v1/accounts/{accountId}",
                        HttpMethod.GET,
                        null, Account.class, accountId);
                
        account = restExchange.getBody();
        
        if (account != null) {
        	putAccountIntoCache(account);
        }
        
        return account;
    }
}
