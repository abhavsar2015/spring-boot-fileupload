package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity 
@Table
public class auction_manufacturer_relation implements Serializable{
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String auction_manufacturer_id;
    private String part_name;
    private String customer_mail;
    private String manufacturer_id;
    private String price;
    private String total_units;
    private String estimated_time;
     
    public auction_manufacturer_relation(){}
     
	public String getAuction_manufacturer_id() {
		return auction_manufacturer_id;
	}
	public void setAuction_manufacturer_id(String auction_manufacturer_id) {
		this.auction_manufacturer_id = auction_manufacturer_id;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public String getCustomer_mail() {
		return customer_mail;
	}
	public void setCustomer_mail(String customer_mail) {
		this.customer_mail = customer_mail;
	}
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotal_units() {
		return total_units;
	}

	public void setTotal_units(String total_units) {
		this.total_units = total_units;
	}

	public String getEstimated_time() {
		return estimated_time;
	}

	public void setEstimated_time(String estimated_time) {
		this.estimated_time = estimated_time;
	}
     
     
}
