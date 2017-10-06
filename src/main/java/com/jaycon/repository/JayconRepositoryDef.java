package com.jaycon.repository;

import java.util.List;
import com.jaycon.model.Order_details;
import com.jaycon.model.auctionTimeRelation;
import com.jaycon.model.auction_parameters;
import com.jaycon.model.paymentCredentials;
import com.jaycon.model.product_parameters;
import com.stripe.model.Invoice;
import com.jaycon.model.User;

public interface JayconRepositoryDef {
	public String addOrderData(Order_details o);
	public String addProductData(product_parameters p);
	public String addLogin(User login);
	public User login_logic(User login);
    public List<product_parameters> getProductDetails(String p);	
    public String setAuctionPermit(String id);
    public product_parameters getAllAuctions(String id);
    public List<auction_parameters> getAuctionsbyId(String id);
    public auction_parameters addAuction(auction_parameters ap);
    public String deleteAuctionById(String id);
    public String setAuctionTime(auctionTimeRelation auctionTime);
    public auctionTimeRelation getAuctionTime(String Id);
    public paymentCredentials addPayment(Invoice p,String quoteId,String Email);
    
}
