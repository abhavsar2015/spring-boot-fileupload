package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Order_details implements Serializable{
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String Order_Id;
	private String part_Name;
    private String firstName;
    private String lastName;
    private String companyName;
    private String shipping_address;
    private String zip;
    private String State;
    private String Country;
    private String phone_No;
    private int mold_price;
    private int part_price;
    private int total_part;
    private int total_part_price;
    private int shipping_price;
    private int total_price;
    private String payment_done;
    private String invoice_no;
    public Order_details(){}
     
    
	public String getOrder_Id() {
		return Order_Id;
	}


	public void setOrder_Id(String order_Id) {
		Order_Id = order_Id;
	}


	public String getPart_Name() {
		return part_Name;
	}
	public void setPart_Name(String part_Name) {
		this.part_Name = part_Name;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getPhone_No() {
		return phone_No;
	}
	public void setPhone_No(String phone_No) {
		this.phone_No = phone_No;
	}
	public int getMold_price() {
		return mold_price;
	}
	public void setMold_price(int mold_price) {
		this.mold_price = mold_price;
	}
	public int getPart_price() {
		return part_price;
	}
	public void setPart_price(int part_price) {
		this.part_price = part_price;
	}
	public int getTotal_part() {
		return total_part;
	}
	public void setTotal_part(int total_part) {
		this.total_part = total_part;
	}
	public int getTotal_part_price() {
		return total_part_price;
	}
	public void setTotal_part_price(int total_part_price) {
		this.total_part_price = total_part_price;
	}
	public int getShipping_price() {
		return shipping_price;
	}
	public void setShipping_price(int shipping_price) {
		this.shipping_price = shipping_price;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public String getPayment_done() {
		return payment_done;
	}
	public void setPayment_done(String payment_done) {
		this.payment_done = payment_done;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
     
     
}
