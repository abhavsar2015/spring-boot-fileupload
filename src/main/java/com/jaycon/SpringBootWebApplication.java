package com.jaycon;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jaycon.service.JayconServicesDef;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

//https://www.agilegroup.co.jp/technote/springboot-fileupload-error-handling.html

@SpringBootApplication
@EnableAutoConfiguration
@EnableEmailTools
public class SpringBootWebApplication  {
	@Autowired
    private JayconServicesDef JayconService;
    //private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	 public static Logger log = Logger.getLogger(SpringBootWebApplication.class.getName());
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
    @Autowired
    private AmazonProperties amazonProperties;

    
    @Bean
    AmazonS3Template amazonS3Template() {
    
        return new AmazonS3Template("my-bucketmake",
                "AKIAIR5ZCV6OXKPJKL3Q",
                "HNZOz9DJZ+InqMKn84nWIGMhEqvAJJEik3/nvWXL");
    }
    
   // @PostConstruct
    //public void sendEmail() throws UnsupportedEncodingException, InterruptedException {
    	//JayconService.sendEmail();
    //}

    

}