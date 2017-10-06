package com.jaycon.adapters;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jaycon.service.JayconServicesDef;
import com.jaycon.service.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurityAdapter extends WebSecurityConfigurerAdapter{
	@Autowired
    private SecurityProperties security;

	@Autowired
	UserServices jayconSer;
	 
	@Value("${app.secret}")
	private String applicationSecret;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
          //.antMatchers("/api/register").permitAll()
          //.antMatchers("/api/activate").permitAll()
         // .antMatchers("/api/activation-send").permitAll()
          //.antMatchers("/api/reset-password").permitAll()
         // .antMatchers("/api/reset-password-change").permitAll()
        //  .antMatchers("/api/autologin").access("hasRole('ROLE_ADMIN')")
        //  .antMatchers("/api/delete").access("hasRole('ROLE_ADMIN')")
        //  .antMatchers("/img/**").permitAll()
        //  .antMatchers("/images/**").permitAll()
        //  .antMatchers("/fonts/**").permitAll()
        .anyRequest().permitAll()
        .and().httpBasic();
        http.csrf().disable();
        /*.and()
            .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
        .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
        .and()
            .rememberMe().key(applicationSecret)
            .tokenValiditySeconds(31536000);*/
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jayconSer).passwordEncoder(new BCryptPasswordEncoder());
    }
}
