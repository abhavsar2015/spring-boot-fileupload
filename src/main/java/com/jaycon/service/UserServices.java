package com.jaycon.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaycon.SpringBootWebApplication;
import com.jaycon.model.User;
import com.jaycon.repository.UserRepository;
@Service
public class UserServices implements UserDetailsService{
	  @Value("${app.user.verification}")
	    private Boolean requireActivation;
	    
	    @Value("${app.secret}")
	    private String applicationSecret;
	    
	    @Autowired
	    private UserRepository repo;
	    
	    @Autowired
	    private HttpSession httpSession;
	    
	    public final String CURRENT_USER_KEY = "CURRENT_USER";
	    
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = repo.findOneByUserNameOrEmail(username, username);
	        if(user == null) {
	            throw new UsernameNotFoundException(username);
	        }
	        if(requireActivation && !user.getActivation_token().equals("1")) {
	        	SpringBootWebApplication.log.error("User [" + username + "] tried to login but is not activated");
	            throw new UsernameNotFoundException(username + " has not been activated yet");
	        }
	        httpSession.setAttribute(CURRENT_USER_KEY, user);
	        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
	        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassWord(), auth);
	    }
	    
	    public void autoLogin(User user) {
	        autoLogin(user.getEmail());
	    }
	    
	    public void autoLogin(String username) {
	        UserDetails userDetails = this.loadUserByUsername(username);
	        
	        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails, null, userDetails.getAuthorities());
	        System.out.println(auth);
	        SecurityContextHolder.getContext().setAuthentication(auth);
	        if(auth.isAuthenticated()) {
	            SecurityContextHolder.getContext().setAuthentication(auth);
	        }
	    }

	    public User register(User user) {
	        user.setPassWord(encodeUserPassword(user.getPassWord()));
            System.out.println(user.getPassWord());
	        if (this.repo.findOneByUserName(user.getEmail()) == null && this.repo.findOneByEmail(user.getEmail()) == null) {
	            String activation = createActivationToken(user, false);
	            user.setActivation_token(activation);
	            
	            this.repo.save(user);
	            return user;
	        }

	        return null;
	    }
	    
	    
	    public String encodeUserPassword(String password) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        return passwordEncoder.encode(password);
	    }
	    
	    public Boolean delete(Long id) {
	        this.repo.delete(id);
	        return true;
	    }
	    
	    public User activate(String activation) {
	        if(activation.equals("1") || activation.length()<5) {
	            return null;
	        }
	        User u = this.repo.findOneByToken(activation);
	        if(u!=null) {
	            u.setActivation_token("1");
	            this.repo.save(u);
	            return u;
	        }
	        return null;
	    }
	    
	    public String createActivationToken(User user, Boolean save) {
	        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	        String activationToken = encoder.encodePassword(user.getEmail(), applicationSecret);
	        if(save) {
	            user.setActivation_token(activationToken);
	            this.repo.save(user);
	        }
	        return activationToken;
	    }
	    
	    public String createResetPasswordToken(User user, Boolean save) {
	        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	        String resetToken = encoder.encodePassword(user.getEmail(), applicationSecret);
	        if(save) {
	            user.setActivation_token(resetToken);
	            this.repo.save(user);
	        }
	        return resetToken;
	    }
	    
	    public User resetActivation(String email) {
	        User u = this.repo.findOneByEmail(email);
	        if(u != null) {
	            createActivationToken(u, true);
	            return u;
	        }
	        return null;
	    }
	    
	    public Boolean resetPassword(User user) {
	        User u = this.repo.findOneByUserName(user.getEmail());
	        if(u != null) {
	            u.setPassWord(encodeUserPassword(user.getPassWord()));
	            u.setActivation_token("1");
	            this.repo.save(u);
	            return true;
	        }
	        return false;
	    }
	    
	    public void updateUser(User user) {
	        updateUser(user.getEmail(), user);
	    }
	    
	    public void updateUser(String userName, User newData) {
	        this.repo.updateUser(
	              
	                newData.getEmail(), 
	                newData.getFirstName(), 
	                newData.getLastName());
	    }
	    
	    public User getLoggedInUser() {
	        return getLoggedInUser(false);
	    }
	    
	    public User getLoggedInUser(Boolean forceFresh) {
	        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = (User) httpSession.getAttribute(CURRENT_USER_KEY);
	        if(forceFresh || httpSession.getAttribute(CURRENT_USER_KEY) == null) {
	            user = this.repo.findOneByUserName(userName);
	            httpSession.setAttribute(CURRENT_USER_KEY, user);
	        }
	        return user;
	    }
	    
	    public void updateLastLogin(String userName) {
	        this.repo.updateLastLogin(userName);
	    }

	    public void updateProfilePicture(User user, String profilePicture) {
	        this.repo.updateProfilePicture(user.getEmail(), profilePicture);
	    }
}
