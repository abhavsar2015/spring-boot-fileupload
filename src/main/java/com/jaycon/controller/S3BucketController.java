package com.jaycon.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.jaycon.service.JayconServicesDef;

@RestController
public class S3BucketController {

    @Autowired
	JayconServicesDef jayconSer;

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

	 private AmazonS3Template amazonS3Template;
	    private String bucketName;
	    
	    @RequestMapping(value = "/api/saveS3upload/{id:.+}", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
		 @ResponseBody
	   public ResponseEntity<Map<String,String>> saveS3UploadFileMulti(
	           @RequestParam("files") MultipartFile uploadfiles,@PathVariable("id") String key) throws Exception {

	       logger.debug("Multiple file upload!");
	       String uploadedFileName = uploadfiles.getOriginalFilename();
	               System.out.println(uploadedFileName);
	               Map<String,String> w=new HashMap();
		              
	       if (StringUtils.isEmpty(uploadedFileName)) {
	           return new ResponseEntity("please select a file!", HttpStatus.OK);
	       }
	       if (!uploadfiles.isEmpty()) {
	           try {
	               ObjectMetadata objectMetadata = new ObjectMetadata();
	               objectMetadata.setContentType(uploadfiles.getContentType());
	               System.out.println( "You failed to upload " + objectMetadata + " => " );
	               // Upload the file for public read
	               AmazonS3Template ast=new AmazonS3Template("my-bucketmake",
	                       "AKIAIR5ZCV6OXKPJKL3Q",
	                       "HNZOz9DJZ+InqMKn84nWIGMhEqvAJJEik3/nvWXL");
	               ast.getAmazonS3Client().putObject(new PutObjectRequest("my-bucketmake", key, uploadfiles.getInputStream(), objectMetadata)
	                       .withCannedAcl(CannedAccessControlList.PublicRead));
	               w.put("errCode", "200");
	               w.put("errDesc", "successful");
	               
	           } catch (Exception e) {
	        	   w.put("errCode", "400");
	               w.put("errDesc", "unsuccessful");
	              
	        	   System.out.println( "You failed to upload " + uploadedFileName + " => "+e );
	               return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	           	
	           }
	       } 
	       else {
	           System.out.println( "You failed to upload " + uploadedFileName + " => " );
	            }          
	       return new ResponseEntity<Map<String,String>>(w, HttpStatus.OK);
	   }
	    
	    @RequestMapping(value = "/api/download/multi/{id:.+}", method = RequestMethod.GET)
		 @ResponseBody
	   public ResponseEntity<byte[]> downloadFileMulti(
			   @PathVariable("id") String key) throws Exception {
		   GetObjectRequest getObjectRequest = new GetObjectRequest("my-bucketmake", key);
		   AmazonS3Template ast=new AmazonS3Template("my-bucketmake",
	               "AKIAIR5ZCV6OXKPJKL3Q",
	               "HNZOz9DJZ+InqMKn84nWIGMhEqvAJJEik3/nvWXL");
	       
		   S3Object s3Object = ast.getAmazonS3Client().getObject(getObjectRequest);
	       System.out.println(s3Object);
	       S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

	       byte[] bytes = IOUtils.toByteArray(objectInputStream);

	       String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

	       HttpHeaders httpHeaders = new HttpHeaders();
	       httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	       httpHeaders.setContentLength(bytes.length);
	       httpHeaders.setContentDispositionFormData("attachment", fileName);
	    	return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	    }
}
