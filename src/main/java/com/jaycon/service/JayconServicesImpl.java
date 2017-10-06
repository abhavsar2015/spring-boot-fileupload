package com.jaycon.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jaycon.AutodeskMethods;
import com.jaycon.SpringBootWebApplication;
import com.jaycon.model.AutodeskCredentials;
import com.jaycon.model.Order_details;
import com.jaycon.model.auctionTimeRelation;
import com.jaycon.model.auction_parameters;
import com.jaycon.model.product_parameters;
import com.jaycon.model.User;
import com.jaycon.repository.JayconRepositoryDef;
import static com.google.common.collect.Lists.newArrayList;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;

@Service("JayconService")
public class JayconServicesImpl implements JayconServicesDef{
	
	@Value("${app.secret}")
    private String applicationSecret;
	
	@Autowired
	JayconRepositoryDef orderrepository;
	
	@Value("${app.user.verification}")
    private Boolean requireActivation;
	
	@Autowired
    private EmailService emailService;
	
	@Autowired
    private HttpSession httpSession;
	
	public final String CURRENT_USER_KEY = "CURRENT_USER"; 
	
	@Override
	public String addOrderdetails(Order_details o)
	{
	    
	    return orderrepository.addOrderData(o);
	}
	
	@Override
	public String addProductData(product_parameters p)
	{
		return orderrepository.addProductData(p);
	}

	@Override
	public void sendEmail() throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("bapurv557@gmail.com",
                        "Apurv Bhavsar"))
                .to(newArrayList(new InternetAddress("apurvbhavsar15@gmail.com",
                        "Cleon I")))
                .subject("You shall die! It's not me, it's Psychohistory")
                .body("Hello Planet!")
                .encoding("UTF-8").build();

        emailService.send(email);
    }
	
	@Override
	public AutodeskCredentials getAutodeskCredentials(MultipartFile f) throws Exception
	{
		AutodeskMethods methods=new AutodeskMethods();
		return methods.getAutodeskCredentials(f);
	}
	
	@Override
	public List<product_parameters>  getProductDetails(String p)
	{	
		return orderrepository.getProductDetails(p);
	}
	
	
	
	@Override
	public User login_logic(User login)
	{	
		return orderrepository.login_logic(login);
	}
	
	@Override
	public String setAuctionPermits(String id)
	{	
		return orderrepository.setAuctionPermit(id);
	}
	
	@Override
	public product_parameters getAllAuctions(String id)
	{	
		return orderrepository.getAllAuctions(id);
	}
	
	@Override
	public List<auction_parameters> getAuctionsbyId(String id)
	{	
		return orderrepository.getAuctionsbyId(id);
	}
	
	@Override
	public auction_parameters addAuction(auction_parameters ap)
	{	
		return orderrepository.addAuction(ap);
	}
	
	@Override
	public String deleteAuctionById(String id)
	{	
		return orderrepository.deleteAuctionById(id);
	}
	
	@Override
	public String setAuctionTime(auctionTimeRelation id)
	{	
		return orderrepository.setAuctionTime(id);
	}
	
	@Override
	public auctionTimeRelation getAuctionTime(String id)
	{	
		return orderrepository.getAuctionTime(id);
	}
	public String encodeUserPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

	
	
	
}
