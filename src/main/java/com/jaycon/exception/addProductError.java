package com.jaycon.exception;

public class addProductError {

    private String errCode;
    private String errDesc;
    private String product_id;
    
    public addProductError(String errCode, String errDesc,String product_id) {
        this.errCode = errCode;
        this.errDesc = errDesc;
        this.product_id =product_id;
    }

    @Override
    public String toString() {
        return "CustomError{" +
                "errCode='" + errCode + '\'' +
                ", errDesc='" + errDesc + '\'' +
                '}';
    }
    
    
    public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
}

