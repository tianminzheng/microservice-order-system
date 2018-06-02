package com.tianyalan.account.event.models;

import com.tianyalan.account.model.Account;

public class AccountChangedEvent{
	//事件类型
    private String type;
    //事件所对应的操作
    private String action;
    //事件对应的领域模型
    private Account account;

    public  AccountChangedEvent(String type, String action, Account account) {
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

}