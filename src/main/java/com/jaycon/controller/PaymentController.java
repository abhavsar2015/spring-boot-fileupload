package com.jaycon.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaycon.exception.addProductError;
import com.jaycon.model.paymentToken;
import com.jaycon.model.product_parameters;
import com.jaycon.service.MailService;
import com.jaycon.service.PaymentServices;
import com.stripe.model.Charge;
import com.stripe.model.Invoice;

@RestController
public class PaymentController {
	private Logger logger = LoggerFactory.getLogger(PaymentController.class);
	 
	@Autowired
	MailService mailService;
	
	@Autowired
	PaymentServices paymentService;
	
	@RequestMapping(value = "/api/doPay", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
   public ResponseEntity<Invoice> payment(
		   @RequestBody paymentToken parameters) throws Exception {

       logger.debug("upload product details");
            
       if (StringUtils.isEmpty(parameters)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       System.out.println(parameters);
      Invoice charge= paymentService.doPayment(parameters);
      
      if(charge==null)
       {
    	   return new ResponseEntity<Invoice>(charge, HttpStatus.OK);
       }
      else
       {
    	  if(charge.getPaid()==true)
    	  {
    		  mailService.sendNewOrder("apurvbhavsar16@gmail.com", parameters.getQuoteId());
    		  mailService.sendInvoice(parameters.getEmail(),parameters.getQuoteId() );
    		  return new ResponseEntity<Invoice>(charge, HttpStatus.OK);
    	 	    		  
    	  }
     	   return new ResponseEntity<Invoice>(charge, HttpStatus.OK);
       }
       
   }
}
