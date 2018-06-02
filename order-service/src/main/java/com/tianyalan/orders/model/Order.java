package com.tianyalan.orders.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {

	@Id
	@GeneratedValue
	private Long id; 	
	private Date createTime; 
	private Long accountId;	
	private String item;	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
