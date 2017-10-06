package com.jaycon.model;

public class AutodeskCredentials {
	private String Urn;
    private String Token;
    
    
    public AutodeskCredentials(){}

	public String getUrn() {
		return Urn;
	}

	public void setUrn(String urn) {
		Urn = urn;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}  
}
