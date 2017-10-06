package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class paymentCredentials implements Serializable {
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String payment_Id;
	private String quoteId;
	private Long amount_due;
	private String attempted;
	private String charge_id;
	private String cust_id;
	private String invoice_number;
	private String customer_email;
	private String total_price;
	
	public paymentCredentials(){}

	public String getPayment_Id() {
		return payment_Id;
	}

	public void setPayment_Id(String payment_Id) {
		this.payment_Id = payment_Id;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public Long getAmount_due() {
		return amount_due;
	}

	public void setAmount_due(Long amount_due) {
		this.amount_due = amount_due;
	}

	public String getAttempted() {
		return attempted;
	}

	public void setAttempted(String attempted) {
		this.attempted = attempted;
	}

	public String getCharge_id() {
		return charge_id;
	}

	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	};
	
	
}
