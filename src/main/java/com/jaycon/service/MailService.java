package com.jaycon.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.jaycon.model.User;

@Service
public class MailService {
    @Value("${app.email.from}")
    private String fromEmail;
    
    @Value("${app.url}")
    private String appUrl;

    @Value("${app.email.support}")
    private String supportEmail;
    
    @Autowired 
    private MailSender mailSender;
    
    public void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setFrom(fromEmail);
            email.setText(text);
            mailSender.send(email);
            System.out.println("SENT EMAIL: TO=" + to + "|SUBJECT:" + subject + "|TEXT:" + text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void sendResetPassword(String to, String token) {
        String url = appUrl + "/user/reset-password-change?token=" + token;
        String subject = "Reset Password";
        String text = "Please click the following link to reset your password: " + url;
        sendMail(to, subject, text);
    }
    
    public void sendNewRegistration(String to, String token) {
        String url = appUrl + "/#!/activation/"+token;
        String subject = "Please activate your account";
        String text = "Please click the following link to activate your account: " + url;
        sendMail(to, subject, text);
    }
   
    public void sendNewOrder(String to, String token) {
        String url =  "https://www.makerpromolds.com/api/download/multi/"+token;
        String subject = "Please click link to get item that is ordered ";
        String text =  url;
        sendMail(to, subject, text);
    }
   
    public void sendInvoice(String to, String token) {
        String url = appUrl + "/#!/api/download/multi/"+token;
        String subject = "Your order successfully placed";
        String text =  url;
        sendMail(to, subject, text);
    }
   
    public void sendNewActivationRequest(String to, String token) {
        sendNewRegistration(to, token);
    }
    
    public void sendErrorEmail(Exception e, HttpServletRequest req, User user) {
        String subject = "Application Error: " + req.getRequestURL();
        String text = "An error occured in your application: " + e + "\r\nFor User:  " + user.getEmail();
        sendMail(supportEmail, subject, text);
    }
}