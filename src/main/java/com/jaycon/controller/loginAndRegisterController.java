package com.jaycon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jaycon.model.User;
import com.jaycon.repository.JayconRepositoryDef;
import com.jaycon.repository.UserRepository;
import com.jaycon.service.JayconServicesDef;
import com.jaycon.service.MailService;
import com.jaycon.service.UserServices;


@RestController
public class loginAndRegisterController {
	 private Logger log = LoggerFactory.getLogger(loginAndRegisterController.class);
	 	@Autowired
		UserServices userService;

	 	@Autowired
		MailService mailService;

	 	 @Value("${app.user.verification}")
	     private Boolean requireActivation;
	 	 
	 	@Autowired
		UserRepository userRepository;
	 	
	 	 @Autowired
	     protected AuthenticationManager authenticationManager;
	 	
	 	@RequestMapping("/api/activation-send")
	    public ModelAndView activationSend(User user) {
	        return new ModelAndView("/api/activation-send");
	    }
	 
	 	@RequestMapping("/login")
	    public String login(User user) {
	        return "api/login";
	    }

	    @RequestMapping("/api/list")
	    public String list(ModelMap map) {
	        Iterable<User> users = this.userRepository.findAll();
	        map.addAttribute("users", users);
	        return "api/list";
	    }

	    @RequestMapping(value = "/api/register", method = RequestMethod.GET)
	    public String register(User user) {
	        return "user/register";
	    }
	    
	    @RequestMapping(value = "/api/register", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	    public ResponseEntity<Map<String,String>> registerPost(@RequestBody User user, BindingResult result) {
	        Map<String,String> w=new HashMap();
	    	if (result.hasErrors()) {
	    		w.put("errCode", "403");
	    		w.put("errDescription", "There is something wrong in the result.");
	            
	    		return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	        }
	        System.out.println(user.getEmail());
	        System.out.println(user.getRole());
	        User registeredUser = userService.register(user);
	        if (registeredUser != null) {
	           mailService.sendNewRegistration(user.getEmail(), registeredUser.getActivation_token());
	            if(!requireActivation) {
	                userService.autoLogin(user.getEmail());
	                w.put("errCode", "202");
		    		w.put("errDescription", "Activation doesnot mendatory");
		            
		    		return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	            }
	            w.put("errCode", "201");
	    		w.put("errDescription", "You need to Activate accouunt by clicking the link sent to your account");
	    		return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	        } else {
	            log.error("User already exists: " + user.getEmail());
	            result.rejectValue("email", "error.alreadyExists", "This username or email already exists, please try to reset password instead.");
	            w.put("errCode", "401");
	    		w.put("errDescription", "Sorry The user already using account.");
	    		return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	        }
	    }
	    
	    @RequestMapping(value = "/api/reset-password")
	    public String resetPasswordEmail(User user) {
	        return "user/reset-password";
	    }
	    
	    @RequestMapping(value = "/api/reset-password", method = RequestMethod.POST)
	    public String resetPasswordEmailPost(User user, BindingResult result) {
	        User u = userRepository.findOneByEmail(user.getEmail());
	        if(u == null) {
	            result.rejectValue("email", "error.doesntExist", "We could not find this email in our databse");
	            return "user/reset-password";
	        } else {
	            String resetToken = userService.createResetPasswordToken(u, true);
	            mailService.sendResetPassword(user.getEmail(), resetToken);
	        }
	        return "user/reset-password-sent";
	    }

	    @RequestMapping(value = "/api/reset-password-change")
	    public String resetPasswordChange(User user, BindingResult result, Model model) {
	        User u = userRepository.findOneByToken(user.getActivation_token());
	        if(user.getActivation_token().equals("1") || u == null) {
	            result.rejectValue("activation", "error.doesntExist", "We could not find this reset password request.");
	        } else {
	            model.addAttribute("userName", u.getEmail());
	        }
	        return "user/reset-password-change";
	    }
	    
	    @RequestMapping(value = "/api/reset-password-change", method = RequestMethod.POST)
	    public ModelAndView resetPasswordChangePost(User user, BindingResult result) {
	        Boolean isChanged = userService.resetPassword(user);
	        if(isChanged) {
	            userService.autoLogin(user.getEmail());
	            return new ModelAndView("redirect:/");
	        } else {
	            return new ModelAndView("api/reset-password-change", "error", "Password could not be changed");
	        }
	    }
	    
	   
	    @RequestMapping(value = "/api/activation-send", method = RequestMethod.POST)
	    public ModelAndView activationSendPost(User user, BindingResult result) {
	        User u = userService.resetActivation(user.getEmail());
	        if(u != null) {
	            mailService.sendNewActivationRequest(u.getEmail(), u.getActivation_token());
	            return new ModelAndView("/api/activation-sent");
	        } else {
	            result.rejectValue("email", "error.doesntExist", "We could not find this email in our databse");
	            return new ModelAndView("/api/activation-send");
	        }
	    }
	    
	    @RequestMapping("/api/delete")
	    public String delete(Long id) {
	        userService.delete(id);
	        return "redirect:/api/list";
	    }
	    
	    @RequestMapping(value ="/api/activate",method = RequestMethod.GET)
	    public ResponseEntity<Map<String,String>> activate(String activation) {
	        User u = userService.activate(activation);
	        Map<String,String> w=new HashMap();
	        if(u != null) {
	            userService.autoLogin(u);
	            w.put("errCode", "200");
	            w.put("errDesc", "account activated");
	            w.put("firstName", u.getFirstName());
	            w.put("lastName",u.getLastName());
	            w.put("email",u.getEmail());
	            w.put("role", u.getRole());
	            return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	        }
	        else
	        {
	        w.put("errCode", "400");
            w.put("errDesc", "Could not activate with this activation code, please contact support");
        
	        return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	        }
	     }
	    
	    @RequestMapping("/api/autologin")
	    public String autoLogin(User user) {
	        userService.autoLogin(user.getEmail());
	        return "redirect:/";
	    }

	    
	    @RequestMapping("/api/edit/{id}")
	    public String edit(@PathVariable("id") String id, User user) {
	        User u;
	        User loggedInUser = userService.getLoggedInUser();
	        if(id == "") {
	            id = loggedInUser.getUser_Id();
	        }
	        if(loggedInUser.getUser_Id() != id && !loggedInUser.isAdmin()) {
	            return "user/premission-denied";
	        } else if (loggedInUser.isAdmin()) {
	            u = userRepository.findOneByEmail(id);
	        } else {
	            u = loggedInUser;
	        }
	        user.setUser_Id(u.getUser_Id());
	        user.setEmail(u.getEmail());
	        user.setFirstName(u.getFirstName());
	        user.setLastName(u.getLastName());
	        return "/user/edit";
	    }
	    
	    @RequestMapping(value = "/api/edit", method = RequestMethod.POST)
	    public String editPost(@Valid User user, BindingResult result) {
	        if (result.hasFieldErrors("email")) {
	            return "/user/edit";
	        }
	        
	        if(userService.getLoggedInUser().isAdmin()) {
	            userService.updateUser(user);
	        } else {
	            userService.updateUser(userService.getLoggedInUser().getEmail(), user);
	        }

	        if (userService.getLoggedInUser().getUser_Id().equals(user.getUser_Id())) {
	            // put updated user to session
	            userService.getLoggedInUser(true);
	        }

	        return "redirect:/api/edit/" + user.getUser_Id() + "?updated";
	    }
	    
}
