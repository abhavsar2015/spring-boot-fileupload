package com.jaycon.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class QuoteCustomerInfo implements Serializable {
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String QuoteCustomerInfo_Id;
	@Id
   private String quoteId;
	@Id
   private String email;
   private String Name;
   private String phoneNo;
   
   public QuoteCustomerInfo(){}

		public String getQuoteId() {
			return quoteId;
		}
		
		public void setQuoteId(String quoteId) {
			this.quoteId = quoteId;
		}
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getName() {
			return Name;
		}
		
		public void setName(String name) {
			Name = name;
		}
		
		public String getPhoneNo() {
			return phoneNo;
		}
		
		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		};
   
   
}
