package com.jaycon.exception;

public class loginError {
	private String errCode;
	private String errDesc;
	private String firstName ;
    private String lastName ;
    private String email ;
    private String userType;

    public loginError(String errCode,String errDesc, String firstName, String lastName, String email, String userType) {
        this.errCode = errCode;
        this.errDesc=errDesc;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.userType=userType;
    }

    @Override
    public String toString() {
        return "CustomError{" +
                "errCode='" + errCode + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType='" + userType + '\'' +
                ", errDesc='" + errDesc + '\'' +
                '}';
    }

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

    
}
