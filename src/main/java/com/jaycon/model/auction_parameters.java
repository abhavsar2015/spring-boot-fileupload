package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class auction_parameters implements Serializable {
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String auction_id;
    private String customer_mailid;
    private String creation_date;
    private String manufacturer_name;
    private String unit_cost;
    private String mold_life;
    private String mold_price;
    private String lead_Time;
    private String total_cost;
    private String part_name;
    private String product_id;
    private String quoteId;
    
    public auction_parameters(){}
    
	public String getQuoteId() {
		return quoteId;
	}

	public String getManufacturer_name() {
		return manufacturer_name;
	}

	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}

	public String getUnit_cost() {
		return unit_cost;
	}

	public void setUnit_cost(String unit_cost) {
		this.unit_cost = unit_cost;
	}

	public String getMold_life() {
		return mold_life;
	}

	public void setMold_life(String mold_life) {
		this.mold_life = mold_life;
	}

	public String getMold_price() {
		return mold_price;
	}

	public void setMold_price(String mold_price) {
		this.mold_price = mold_price;
	}

	public String getLead_Time() {
		return lead_Time;
	}

	public void setLead_Time(String lead_Time) {
		this.lead_Time = lead_Time;
	}

	public String getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(String total_cost) {
		this.total_cost = total_cost;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getAuction_id() {
		return auction_id;
	}

	public void setAuction_id(String auction_id) {
		this.auction_id = auction_id;
	}

	public String getCustomer_mailid() {
		return customer_mailid;
	}

	public void setCustomer_mailid(String customer_mailid) {
		this.customer_mailid = customer_mailid;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
}
