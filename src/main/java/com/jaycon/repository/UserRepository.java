package com.jaycon.repository;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jaycon.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
	
	
	User findOneByUserName(String name);
    User findOneByEmail(String email);
    User findOneByUserNameOrEmail(String username, String email);
    User findOneByToken(String token);
 
 
    int updateUser(
            
             String email,
             String firstName,
             String lastName
           );
    
   
    int updateLastLogin(String userName);
    int updateToken(User u);
  
    int updateProfilePicture(String userName, String profilePicture);
}