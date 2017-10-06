package com.jaycon.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaycon.model.Order_details;
import com.jaycon.model.paymentToken;
import com.jaycon.repository.JayconRepositoryDef;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;

@Service
public class PaymentServices implements PaymentServicesDef{
	
	
	@Autowired
	JayconRepositoryDef orderrepository;
	
	@Override
	public Invoice doPayment(paymentToken o) throws AuthenticationException, InvalidRequestException,
    APIConnectionException, CardException, APIException   {
		Stripe.apiKey = "sk_test_XPfykG36tDqWm3lcqeTvJzcB"; // stripe public
        // test key
		System.out.println(o.getEmail());
		System.out.println(o.getToken());
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("email", (String)o.getEmail());
		customerParams.put("source", (String)o.getToken());
		Customer customer = Customer.create(customerParams);
		
		
		System.out.println(o.getAmount());
		final Map<String, Object> chargeParams = new HashMap<String, Object>();
		
		chargeParams.put("amount", o.getAmount());
		chargeParams.put("currency", "USD");
		chargeParams.put("description", "Jaycon2 checkout");
		chargeParams.put("customer", customer.getId());
		InvoiceItem.create(chargeParams);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customer.getId());
		Invoice invoice=Invoice.create(params).pay();
		if(invoice.getPaid()==true)
		{
			orderrepository.addPayment(invoice, o.quoteId, o.getEmail());
		}
		
	    System.out.println(invoice);
		return invoice;
	}
}
