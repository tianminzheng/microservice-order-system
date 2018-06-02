package com.tianyalan.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tianyalan.account.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>  {

}
