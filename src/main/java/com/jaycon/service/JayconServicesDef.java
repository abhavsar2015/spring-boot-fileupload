package com.jaycon.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.jaycon.model.AutodeskCredentials;
import com.jaycon.model.Order_details;
import com.jaycon.model.auctionTimeRelation;
import com.jaycon.model.auction_parameters;
import com.jaycon.model.product_parameters;
import com.jaycon.model.User;

public interface JayconServicesDef  {
    public String addOrderdetails(Order_details o);
    public String addProductData(product_parameters p);     
    public AutodeskCredentials getAutodeskCredentials(MultipartFile f) throws Exception;
    public void sendEmail() throws UnsupportedEncodingException;
    //public String addLoginData(User login);
    public User login_logic(User login);
    public List<product_parameters> getProductDetails(String p);
    public String setAuctionPermits(String id);
    public product_parameters getAllAuctions(String id);
    public List<auction_parameters> getAuctionsbyId(String id);
    public auction_parameters addAuction(auction_parameters ap);
    public String deleteAuctionById(String id);
    public String setAuctionTime(auctionTimeRelation id);
    public auctionTimeRelation getAuctionTime(String id);
    
}
