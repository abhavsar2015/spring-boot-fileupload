package com.jaycon.model;

import java.io.Serializable;

public class paymentToken implements Serializable {
	
	public paymentToken(){}
	public Long amount;
    public String email;
    public String token;
    public String quoteId;
    
    
    
	public String getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
}
