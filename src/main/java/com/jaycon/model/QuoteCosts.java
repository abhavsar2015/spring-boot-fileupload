package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class QuoteCosts implements Serializable {
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String QuoteCosts_Id;
	@Id
	private String quoteId;
	private String mold_cost;
    private String quote_price;
    private String mold_core_cost;
    private String mold_base_cost;
    private String undercut_cost;
    private String total_cost;
    private String cost_per_part;
    private String shipping_cost;
	
    public QuoteCosts(){}

	public String getQuoteCosts_Id() {
		return QuoteCosts_Id;
	}
	
	public void setQuoteCosts_Id(String quoteCosts_Id) {
		QuoteCosts_Id = quoteCosts_Id;
	}
	
	public String getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}
	public String getMold_cost() {
		return mold_cost;
	}
	public void setMold_cost(String mold_cost) {
		this.mold_cost = mold_cost;
	}
	public String getQuote_price() {
		return quote_price;
	}
	public void setQuote_price(String quote_price) {
		this.quote_price = quote_price;
	}
	public String getMold_core_cost() {
		return mold_core_cost;
	}
	public void setMold_core_cost(String mold_core_cost) {
		this.mold_core_cost = mold_core_cost;
	}
	public String getMold_base_cost() {
		return mold_base_cost;
	}
	public void setMold_base_cost(String mold_base_cost) {
		this.mold_base_cost = mold_base_cost;
	}
	public String getUndercut_cost() {
		return undercut_cost;
	}
	public void setUndercut_cost(String undercut_cost) {
		this.undercut_cost = undercut_cost;
	}
	public String getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(String total_cost) {
		this.total_cost = total_cost;
	}
	public String getCost_per_part() {
		return cost_per_part;
	}
	public void setCost_per_part(String cost_per_part) {
		this.cost_per_part = cost_per_part;
	}
	public String getShipping_cost() {
		return shipping_cost;
	}
	public void setShipping_cost(String shipping_cost) {
		this.shipping_cost = shipping_cost;
	}
    
    
}
