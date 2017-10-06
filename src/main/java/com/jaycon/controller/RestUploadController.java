package com.jaycon.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.jaycon.AmazonS3Template;
import com.jaycon.exception.CustomError;
import com.jaycon.exception.addProductError;
import com.jaycon.exception.loginError;
import com.jaycon.exception.productDetailsError;
import com.jaycon.model.AutodeskCredentials;
import com.jaycon.model.Metadata1;
import com.jaycon.model.Order_details;
import com.jaycon.model.auctionTimeRelation;
import com.jaycon.model.auction_parameters;
import com.jaycon.model.product_parameters;
import com.jaycon.model.User;
import com.jaycon.service.JayconServicesDef;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

     //Save the uploaded file to this folder
   


    @Autowired
	JayconServicesDef jayconSer;

   
    
   
    // Multiple file upload
    @RequestMapping(value = "/api/upload/multi", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @ResponseBody
    public ResponseEntity<AutodeskCredentials> uploadFileMulti(
            @RequestParam("files") MultipartFile uploadfiles) throws Exception {

        logger.debug("Multiple file upload!");
        AutodeskCredentials credentials=new AutodeskCredentials();
        String uploadedFileName = uploadfiles.getOriginalFilename();
                System.out.println(uploadedFileName);
                
        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try{
             credentials=jayconSer.getAutodeskCredentials(uploadfiles);
             System.out.println(credentials.getToken());
             System.out.println(credentials.getUrn());
             return new ResponseEntity<AutodeskCredentials>(credentials, HttpStatus.OK);
             
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return new ResponseEntity<AutodeskCredentials>(credentials, HttpStatus.OK);
        
    }
    
    
    
    @RequestMapping(value = "/api/addProductDetails", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
   public ResponseEntity<addProductError> addProductDetails(
		   @RequestBody product_parameters parameters) throws Exception {

       logger.debug("upload product details");
       product_parameters s=new product_parameters();        
       if (StringUtils.isEmpty(parameters)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       String id=jayconSer.addProductData(parameters);
	   System.out.println(id);
       if(id==null)
       {
    	   return new ResponseEntity<addProductError>(new addProductError("400","there is something wrong",id), HttpStatus.OK);
       }
       else
       {
     	   return new ResponseEntity<addProductError>(new addProductError("200","every thing is good",id), HttpStatus.OK);
       }
       
   }
    
    @RequestMapping(value = "/api/addOrderDetails", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
    public ResponseEntity<String> addOrderDetails(
		   @RequestBody Order_details order) throws Exception {

       logger.debug("add order details");
       String s="";        
       if (StringUtils.isEmpty(order)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       
       s=jayconSer.addOrderdetails(order);
	   System.out.println(s);

       return new ResponseEntity<String>(s, HttpStatus.OK);
   }
    
    @RequestMapping(value = "/api/signup", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<CustomError> Signup(
		   @RequestBody User login) throws Exception {

       logger.debug("add order details");
       	String s="";        
       	if (StringUtils.isEmpty(login)) {
       		return new ResponseEntity("please add proper data!", HttpStatus.OK);
       	}
       	CustomError error;       
       	//s=jayconSer.addLoginData(login);
       	System.out.println(s);
       	if(s.equals("available"))
       		{
       		return new ResponseEntity<CustomError>(new CustomError("400","user data already availabe"), HttpStatus.OK);
       		}
       	else
       		{
       		return new ResponseEntity<CustomError>(new CustomError("200","successful"), HttpStatus.OK);
       		}
       	}
    
    @RequestMapping(value = "/api/login", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<loginError> login(@RequestBody User login) throws Exception {

       logger.debug("add order details");
       User s=new User();        
       if (StringUtils.isEmpty(login)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       System.out.println(login.getEmail());
       System.out.println(login.getPassWord());
       s=jayconSer.login_logic(login);
	   System.out.println(s);
       if(s==null)
       {
    	   return new ResponseEntity<loginError>(new loginError("400","Please Signup","","","",""), HttpStatus.OK);      
       }
       else
       {
    	   return new ResponseEntity<loginError>(new loginError("200","successful",s.getFirstName(),s.getLastName(),s.getEmail(),s.getRole()), HttpStatus.OK);         
       }
       
   }
	
	@RequestMapping(value = "/api/setAuctionTime", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> setAuctionTime(
		   @RequestBody auctionTimeRelation auction) throws Exception {

       logger.debug("set Auction Time");
               
       if (StringUtils.isEmpty(auction)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       System.out.println(auction.getAuctionStartDate());
       String s=jayconSer.setAuctionTime(auction);
	   System.out.println(s);
	   Map<String,String> w=new HashMap();
	   if(s.isEmpty())
       {
		   w.put("errCode", "400");
	       w.put("errDesc", "Data is empty");
    	   return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);      
       }
       else
       {
    	   w.put("errCode", "200");
	       w.put("errDesc", "everything is good");
	       w.put(" auctionId",s);
    	   return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);         
       }
       
   }

	
	@RequestMapping(value = "/api/getPartDetails/{id:.+}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<productDetailsError>> getPartDetails(
			@PathVariable("id") String p) throws Exception {

       logger.debug("add order details");
       if (StringUtils.isEmpty(p)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       
       System.out.println(p);
       List<product_parameters> s=jayconSer.getProductDetails(p);
       System.out.println(s);
	   List<productDetailsError> s1=new ArrayList();
       for(product_parameters p1:s)
	   {
    	   productDetailsError pe=new productDetailsError();
    	   pe.setEmail(p1.getEmail());
    	   pe.setErrCode("200");
    	   pe.setErrDesc("operation is successful");
    	   pe.setMold_cost(p1.getQuoteCosts().getMold_cost());
    	   pe.setProduct_material(p1.getMaterial());
    	   pe.setMold_material(p1.getMolding_tooling());
    	   pe.setPartName(p1.getPart_name());
    	   pe.setTotal_cost(p1.getQuoteCosts().getTotal_cost());
    	   pe.setShipping_cost(p1.getQuoteCosts().getShipping_cost());
    	   pe.setX(p1.getX());
    	   pe.setY(p1.getY());
    	   pe.setZ(p1.getZ());
    	   pe.setVolume(p1.getVolume());
    	   pe.setProduct_id(p1.getProduct_Id());
    	   pe.setQuoteId(p1.getQuoteId());
		   s1.add(pe);
	   }
       
       if(s1.isEmpty())
       		{
    	   		return new ResponseEntity<List<productDetailsError>>(s1, HttpStatus.OK);      
       		}
       		else
       		{
       			return new ResponseEntity<List<productDetailsError>>(s1, HttpStatus.OK);         
       		}
   }

	@RequestMapping(value = "/api/getAuctionTime/{id}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> getAuctionTime(
			@PathVariable("id") String p) throws Exception {

       logger.debug("add order details");
       if (StringUtils.isEmpty(p)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       
       System.out.println(p);
       auctionTimeRelation s=jayconSer.getAuctionTime(p);
       System.out.println(s);
	   Map<String,String> w=new Hashtable();
	   w.put("errCode", "200");
	   w.put("errDesc","everything is good");
	   w.put("quoteId", s.getQuoteId());
	   w.put("auctionStartDate",s.getAuctionStartDate() );
       return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);         
       		
   }

	@RequestMapping(value = "/api/setAuctionPermits/{id}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> setAuctionPermit(
			@PathVariable("id") String p) throws Exception {

       logger.debug("add order details");
       if (StringUtils.isEmpty(p)) {
           return new ResponseEntity("please add proper data!", HttpStatus.OK);
       }
       
       System.out.println(p);
       String s=jayconSer.setAuctionPermits(p);
       System.out.println(s);
	   //List<Map<String,String>> s1=new ArrayList();
       Map<String,String> w=new HashMap();
       w.put("errCode", "200");
       w.put("errDesc","successfully done");
       w.put("id", p);
	   //s1.add(w);
       return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);         
       	
   }

	@RequestMapping(value = "/api/getAllAuctions/{id}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> getAllAuctions(@PathVariable("id") String p) throws Exception {

       logger.debug("add order details");
      
       System.out.println();
       product_parameters s=jayconSer.getAllAuctions(p);
       System.out.println(s);
       Map<String,String> w=new HashMap();
       w.put("errCode", "200");
       w.put("errDesc","successfully done");
       w.put("part_name",s.getPart_name());
       w.put("part_material",s.getMaterial());
       w.put("mold_material",s.getMolding_tooling());
       w.put("userId",s.getEmail());
       w.put("quantity",s.getTotal_units());
       w.put("color",s.getColor());
       w.put("quoteId",s.getQuoteId());
       w.put("productId",s.getProduct_Id());
       
       // w.put("id", p);
	   //s1.add(w);
       return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);         
       	
   }
	@RequestMapping(value = "/api/deleteAuctionbyId/{id}/{quoteId}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<Map<String,String>>> deleteAuctionbyId(@PathVariable("id") String p,@PathVariable("quoteId") String quoteId) throws Exception {
       logger.debug("add order details");
       System.out.println();
       String t=jayconSer.deleteAuctionById(p);
       List<auction_parameters> s=jayconSer.getAuctionsbyId(quoteId);
       
       
       System.out.println(s);
       if(s.isEmpty())
       {
    	   Map<String,String> w=new HashMap();
    	   List<Map<String,String>> l=new ArrayList();
    	   w.put("errCode", "400");
           w.put("errDesc","no data available");
           l.add(w);
    	   return new ResponseEntity<List<Map<String,String>>>(l, HttpStatus.OK); 
       }
       else{
    	   List<Map<String,String>> l=new ArrayList();
    	   for(auction_parameters a:s)
    	   {
    		   Map<String,String> w=new HashMap();
    		   w.put("errCode", "200");
       		   w.put("errDesc","data is available to show");
       		   w.put("auction_id", a.getAuction_id());
       		   w.put("customer_mailid", a.getCustomer_mailid());
       		   w.put("creation_date", a.getCreation_date());
       		   w.put("part_name", a.getPart_name());
       		   w.put("product_id", a.getProduct_id());
       		   w.put("quoteId", a.getQuoteId());
       		   w.put("unit_cost", a.getUnit_cost()); 
       		   w.put("mold_life", a.getMold_life());
       		   w.put("mold_price", a.getMold_price());
       		   w.put("lead_Time", a.getLead_Time());
       		   w.put("total_cost", a.getTotal_cost());
       		   w.put("mfg_name", a.getManufacturer_name());
       		   l.add(w);
    	   }
    	   return new ResponseEntity<List<Map<String,String>>>(l, HttpStatus.OK);         
       }      	   
	}
	@RequestMapping(value = "/api/getAllAuctionbyId/{id}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<Map<String,String>>> getAuctionbyId(@PathVariable("id") String p) throws Exception {
       logger.debug("add order details");
       System.out.println();
       List<auction_parameters> s=jayconSer.getAuctionsbyId(p);
       
       
       System.out.println(s);
       if(s.isEmpty())
       {
    	   Map<String,String> w=new HashMap();
    	   List<Map<String,String>> l=new ArrayList();
    	   w.put("errCode", "400");
           w.put("errDesc","no data available");
           l.add(w);
    	   return new ResponseEntity<List<Map<String,String>>>(l, HttpStatus.OK); 
       }
       else{
    	   List<Map<String,String>> l=new ArrayList();
    	   for(auction_parameters a:s)
    	   {
    		   Map<String,String> w=new HashMap();
    		   w.put("errCode", "200");
       		   w.put("errDesc","data is available to show");
       		   w.put("auction_id", a.getAuction_id());
       		   w.put("customer_mailid", a.getCustomer_mailid());
       		   w.put("creation_date", a.getCreation_date());
       		   w.put("part_name", a.getPart_name());
       		   w.put("product_id", a.getProduct_id());
       		   w.put("quoteId", a.getQuoteId());
       		   w.put("unit_cost", a.getUnit_cost()); 
       		   w.put("mold_life", a.getMold_life());
       		   w.put("mold_price", a.getMold_price());
       		   w.put("lead_Time", a.getLead_Time());
       		   w.put("total_cost", a.getTotal_cost());
       		   w.put("mfg_name", a.getManufacturer_name());
       		   l.add(w);
    	   }
    	   return new ResponseEntity<List<Map<String,String>>>(l, HttpStatus.OK);         
       }      	   
       }

    @RequestMapping(value = "/api/addAuction", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
   	@ResponseBody
   	public ResponseEntity<List<Map<String,String>>> addAuction(@RequestBody auction_parameters auction) throws Exception {
          logger.debug("add order details");
          System.out.println();
          
          auction_parameters ap1=jayconSer.addAuction(auction);
          List<auction_parameters> s=jayconSer.getAuctionsbyId(ap1.getQuoteId());
          
          System.out.println(s);
          if(s.isEmpty())
          {
       	   Map<String,String> w=new HashMap();
       	   List<Map<String,String>> l=new ArrayList();
       	   w.put("errCode", "400");
           w.put("errDesc","operation unsuccessful");
              l.add(w);
       	   return new ResponseEntity<List<Map<String,String>>>(l, HttpStatus.OK); 
          }
          else{
       	   List<Map<String,String>> l=new ArrayList();
       	   for(auction_parameters a:s)
       	   {
       		   Map<String,String> w=new HashMap();
       		   w.put("errCode", "200");
       		   w.put("errDesc","data is available to show");
       		   w.put("auction_id", a.getAuction_id());
       		   w.put("customer_mailid", a.getCustomer_mailid());
       		   w.put("creation_date", a.getCreation_date());
       		   w.put("part_name", a.getPart_name());
       		   w.put("product_id", a.getProduct_id());
       		   w.put("quoteId", a.getQuoteId());
       		   w.put("unit_cost", a.getUnit_cost()); 
       		   w.put("mold_life", a.getMold_life());
       		   w.put("mold_price", a.getMold_price());
       		   w.put("lead_Time", a.getLead_Time());
       		   w.put("total_cost", a.getTotal_cost());
       		   w.put("mfg_name", a.getManufacturer_name());
       		   l.add(w);
       	   }
       	   return new ResponseEntity<List<Map<String,String>>>(l, HttpStatus.OK);         
       	         	   
          }

   }

   // @RequestMapping(value = "/Metadata", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	// @ResponseBody
	 //public ResponseEntity<List<Metadata1>> getMatadata(@RequestBody Metadata1 metadata1 ) {
	       
	       // List<Metadata1> metadata= fileSer.getAllMetadata();
	       // System.out.println(metadata);
		 //   return new ResponseEntity<List<Metadata1>>(metadata, HttpStatus.OK);
	    //}
   /* private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
    for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; 
            }
            Date dNow = new Date( );
            SimpleDateFormat ft = 
            new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
            Metadata1 m=new Metadata1();
            m.setFiles(file.getOriginalFilename());
            m.setCurrentTime(ft.format(dNow));
           // fileSer.addMetadata(m);
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }*/

    }

