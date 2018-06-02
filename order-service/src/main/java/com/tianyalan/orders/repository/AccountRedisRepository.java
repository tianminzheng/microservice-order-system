package com.tianyalan.orders.repository;

import com.tianyalan.orders.model.Account;

public interface AccountRedisRepository {
    void saveAccount(Account account);
    
    void updateAccount(Account account);
    
    void deleteAccount(Long accountId);
    
    Account findAccountById(Long accountId);
}
