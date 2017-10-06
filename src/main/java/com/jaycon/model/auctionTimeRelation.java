package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table
public class auctionTimeRelation implements Serializable {
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
      private String auctionTimeId;
      private String product_Id;
      private String quoteId;
      private String auctionStartDate;
	
      public String getAuctionTimeId() {
		return auctionTimeId;
      }
      
      public void setAuctionTimeId(String auctionTimeId) {
		this.auctionTimeId = auctionTimeId;
      }
      
      public String getProduct_Id() {
		return product_Id;
      }
      
      public void setProduct_Id(String product_Id) {
		this.product_Id = product_Id;
      }
      
      public String getQuoteId() {
		return quoteId;
		}
	
      public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
      	}
      
      public String getAuctionStartDate() {
		return auctionStartDate;
      	}
      
      public void setAuctionStartDate(String auctionStartDate) {
		this.auctionStartDate = auctionStartDate;
      	}
      
}
