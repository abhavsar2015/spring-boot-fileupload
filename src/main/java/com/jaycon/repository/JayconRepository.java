package com.jaycon.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.jaycon.model.Order_details;
import com.jaycon.model.auctionTimeRelation;
import com.jaycon.model.auction_parameters;
import com.jaycon.model.paymentCredentials;
import com.jaycon.model.product_parameters;
import com.stripe.model.Invoice;
import com.jaycon.model.User;

@Repository
public class JayconRepository implements JayconRepositoryDef {
	 @PersistenceContext(unitName="entityManagerFactory")
	    public EntityManager em;
	    private TransactionTemplate transactionTemplate;
	   
	     
	    
	        public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
	    
	            this.transactionTemplate = transactionTemplate;
	    
	        }

	 public EntityManager getEm() {
	    		return em;
	   }
	 
	 public void setEm(EntityManager em) {
	    		this.em = em;
	   }
	
	 
	@Override
	@Transactional
	public String addOrderData(Order_details o)
	{
		EntityManager em1 = getEm();
		em1.persist(o);
		return o.getOrder_Id();
	}
	
	@Override
	@Transactional
	public String addProductData(product_parameters p)
	{
		EntityManager em1 = getEm();
		em1.persist(p);
		return p.getProduct_Id();
		
	}
	
	@Override
	@Transactional
	public String setAuctionTime(auctionTimeRelation p)
	{
		EntityManager em1 = getEm();
		em1.persist(p);
		return p.getAuctionTimeId();
		
	}
	
	@Override
	@Transactional
	public auctionTimeRelation getAuctionTime(String p)
	{
		EntityManager em1 = getEm();
		TypedQuery<auctionTimeRelation> query = em.createQuery("SELECT c FROM auctionTimeRelation c WHERE c.quoteId = :quoteid", auctionTimeRelation.class);
		
		List<auctionTimeRelation> s=query.setParameter("quoteid", p).getResultList();
		System.out.println(p);
		if(s.isEmpty())
		{
		return null;
		}
		else {
			return s.get(0);
				
		}
	}
	
	@Override
	@Transactional
	public String addLogin(User p)
	{
		EntityManager em1 = getEm();
		TypedQuery<User> query = em.createQuery("SELECT c FROM login_details c WHERE c.email = :email", User.class);
		List<User> s=query.setParameter("email", p.getEmail()).getResultList();
		if(s.isEmpty())
		{
			em1.persist(p);	
		    return "success";
		}
		else
		{
			return "available";
		}
	 }
	
	@Override
	@Transactional
	public paymentCredentials addPayment(Invoice p,String quoteId,String Email)
	{
		EntityManager em1 = getEm();
		TypedQuery<paymentCredentials> query = em.createQuery("SELECT c FROM paymentCredentials c WHERE c.quoteId = :quoteId", paymentCredentials.class);
		List<paymentCredentials> s=query.setParameter("quoteId", quoteId).getResultList();
		if(s.isEmpty())
		{
			paymentCredentials pay=new paymentCredentials();
			pay.setAmount_due(p.getAmountDue());
			pay.setCustomer_email(Email);
			pay.setCust_id(p.getCustomer());
			pay.setInvoice_number(p.getNumber());
			pay.setAttempted(p.getAttempted().toString());
			pay.setQuoteId(quoteId);
			pay.setTotal_price(""+p.getTotal()+"");
			pay.setCharge_id(p.getCharge());
			em1.persist(pay);	
		    return pay;
		}
		else
		{
			return null;
		}
	 }
	
	@Override
	@Transactional
	public User login_logic(User p)
	{
		EntityManager em1 = getEm();
		TypedQuery<User> query = em.createQuery("SELECT c FROM User c WHERE c.email = :email and c.activation_token = :activation ", User.class);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		List<User> s=query.setParameter("email", p.getEmail()).setParameter("activation", "1").getResultList();
		System.out.println(p.getEmail());
		System.out.println(p.getPassWord());
		System.out.println(passwordEncoder.matches(p.getPassWord(), s.get(0).getPassWord()));
		if(s.isEmpty())
		{
			
		   return null; 
		}
		else if(s.get(0).getEmail().equals(p.getEmail()) && passwordEncoder.matches(p.getPassWord(), s.get(0).getPassWord())==true)
		{
			return s.get(0);
		}
		else
		{
			 return null;
		}
	 }
	
	@Override
	@Transactional
	public List<product_parameters> getProductDetails(String p)
	{
		
		EntityManager em1 = getEm();
		TypedQuery<product_parameters> query = em.createQuery("SELECT p FROM product_parameters p WHERE p.email= :email and auction_permit= :auction_permit1", product_parameters.class);
		System.out.println("apurv quer");
		System.out.println(p);
		List<product_parameters> s=query.setParameter("email", p).setParameter("auction_permit1", "YES").getResultList();
		System.out.println(p);
		System.out.println(s.get(0));
		if(s.isEmpty())
		{
			System.out.println("after_loging");	
			return null;
		}
		else
		{
			return s;
		}
	 }
	
	@Override
	@Transactional
	public String setAuctionPermit(String id){
		 product_parameters product_parameters = em.find(product_parameters.class, id);
		 product_parameters.setAuction_permit("YES");
		 return "successful";
	}
	
	@Override
	@Transactional
	public product_parameters getAllAuctions(String id){
		EntityManager em1 = getEm();
		TypedQuery<product_parameters> query = em.createQuery("SELECT p FROM product_parameters p WHERE p.quoteId = :quoteid and auction_permit= :auction_permit1", product_parameters.class);
		System.out.println("apurv quer");
		System.out.println(id);
		List<product_parameters> s=query.setParameter("quoteid", id).setParameter("auction_permit1", "YES").getResultList();
		 if(s.isEmpty())
		 {
			 return null;
		 }
		 else
		 {
			 return s.get(0);	 
		 }
		
	}
	@Override
	@Transactional
	public List<auction_parameters> getAuctionsbyId(String id){
		EntityManager em1 = getEm();
		TypedQuery<auction_parameters> query = em.createQuery("SELECT p FROM auction_parameters p WHERE p.quoteId = :quoteid ", auction_parameters.class);
		System.out.println("apurv quer");
		System.out.println(id);
		List<auction_parameters> s=query.setParameter("quoteid", id).getResultList();
		

				return s; 
		 
	}
	@Override
	@Transactional
	public auction_parameters addAuction(auction_parameters ap){
		EntityManager em1 = getEm();
		em1.persist(ap);
		return ap; 
	}
	
	@Override
	@Transactional
	public String deleteAuctionById(String id){
		EntityManager em1 = getEm();
		auction_parameters auction = em1.find(auction_parameters.class, id);
		
		em1.remove(auction);
		return id; 
	}

	
}
