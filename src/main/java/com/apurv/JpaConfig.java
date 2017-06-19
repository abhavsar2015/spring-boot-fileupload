package com.apurv;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
   
	@Bean(name="serv1")
	public LocalContainerEntityManagerFactoryBean entityManagerBean(){
		LocalContainerEntityManagerFactoryBean em=new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.apurv.model");
		JpaVendorAdapter jp=new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(jp);
		em.setJpaProperties(properties());
		return em;
	}
	@Bean 
	public DataSource dataSource(){
		DriverManagerDataSource ds=new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/jpa_example");
		ds.setUsername("root");
		ds.setPassword("root");;
       return ds;
	}
	@Bean(name="serv")
	public PlatformTransactionManager txnManager(EntityManagerFactory em)
	{
		JpaTransactionManager jtm=new JpaTransactionManager(em);
		return jtm;
	}
	private Properties properties(){
		Properties ps=new Properties();
		ps.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		ps.setProperty("hibernate.hbm2ddl.auto", "validate");
		ps.setProperty("hibernate.show_sql", "true");
		ps.setProperty("hibernate.format_sql", "true");
		
		return ps;
	}
}
