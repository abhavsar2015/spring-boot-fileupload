package com.jaycon.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class User  {
	 @Id
	 @GenericGenerator(name = "sequence", strategy = "uuid2") 
	    @GeneratedValue(generator = "sequence")
	    private String User_Id;
		
        private String firstName ;
        private String lastName ;
        private String email;
        private String passWord;
        private String role;
        private String activation_token;
        
        
		public String getActivation_token() {
			return activation_token;
		}
		public void setActivation_token(String activation_token) {
			this.activation_token = activation_token;
		}
		public String getUser_Id() {
			return User_Id;
		}
		public void setUser_Id(String user_Id) {
			User_Id = user_Id;
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
		public String getPassWord() {
			return passWord;
		}
		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}
		
        public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public boolean isEmpty()
        {
        	if(this.firstName==null  && this.lastName==null && this.email==null && this.passWord==null && this.role==null)
        	{
        		return true;
        	}
        	else
        	{
        		return false;
        	}
        }
        
        public Boolean isAdmin() {
            return this.role.equals("admin");
        }
        
        public void setEmpty()
        {
        	this.firstName=null;
        	this.lastName=null;
        	this.email=null;
        	this.passWord=null;
        	this.role=null;
        	this.User_Id=null;	
        }
}
