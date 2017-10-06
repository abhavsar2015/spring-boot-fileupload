package com.jaycon.service;

import java.util.Map;

import com.jaycon.model.Order_details;
import com.jaycon.model.paymentToken;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Invoice;

public interface PaymentServicesDef {
	public Invoice doPayment(paymentToken o) throws AuthenticationException, InvalidRequestException,
    APIConnectionException, CardException, APIException;
	
}
