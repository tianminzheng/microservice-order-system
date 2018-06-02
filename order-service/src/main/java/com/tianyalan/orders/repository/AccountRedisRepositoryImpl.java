package com.tianyalan.orders.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.tianyalan.orders.model.Account;

import javax.annotation.PostConstruct;

@Repository
public class AccountRedisRepositoryImpl implements AccountRedisRepository {
    private static final String HASH_NAME ="account";

    private RedisTemplate<String, Account> redisTemplate;
    private HashOperations<String, Long, Account> hashOperations;

    public AccountRedisRepositoryImpl(){
        super();
    }

    @Autowired
    private AccountRedisRepositoryImpl(RedisTemplate<String, Account> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveAccount(Account account) {
        hashOperations.put(HASH_NAME, account.getId(), account);
    }

    @Override
    public void updateAccount(Account account) {
        hashOperations.put(HASH_NAME, account.getId(), account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        hashOperations.delete(HASH_NAME, accountId);
    }

    @Override
    public Account findAccountById(Long accountId) {
       return (Account) hashOperations.get(HASH_NAME, accountId);
    }
}
