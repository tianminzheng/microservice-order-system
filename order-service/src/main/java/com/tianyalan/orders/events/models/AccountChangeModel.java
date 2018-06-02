package com.tianyalan.orders.events.models;

import com.tianyalan.orders.model.Account;

public class AccountChangeModel{
    private String type;
    private String action;
    private Account account;

    public AccountChangeModel(){
        super();
    }

    public  AccountChangeModel(String type, String action, Account account) {
        super();
        this.type   = type;
        this.action = action;
        this.account = account;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
    @Override
    public String toString() {
        return "OrganizationChangeModel [type=" + type +
                ", action=" + action +
                ", accountId="  + account.getId() +"]";
    }
}
