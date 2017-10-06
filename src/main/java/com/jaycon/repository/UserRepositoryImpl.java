package com.jaycon.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.jaycon.model.User;
import com.jaycon.model.product_parameters;
@Repository
@Primary
public class UserRepositoryImpl implements UserRepository {
	@PersistenceContext(unitName="entityManagerFactory")
	public EntityManager em;
    private TransactionTemplate transactionTemplate;
   
     
 public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
    
            this.transactionTemplate = transactionTemplate;
    
        }

 public EntityManager getEm() {
    		return em;
   }
 
 public void setEm(EntityManager em) {
    		this.em = em;
   }

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends User> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public <S extends User> S save(S arg0) {
		EntityManager em1 = getEm();
		em1.persist(arg0);
		
		return arg0;
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOneByUserName(String name) {
		TypedQuery<User> query = em.createQuery("SELECT p FROM User p WHERE p.email = :emailer ", User.class);
		System.out.println("apurv quer");
		System.out.println(name);
		List<User> s=query.setParameter("emailer", name).getResultList();
		if(s.isEmpty())
		 {
			 return null;
		 }
		 else
		 {
			 return s.get(0);	 
		 }
	
	}

	@Override
	public User findOneByEmail(String email) {
		TypedQuery<User> query = em.createQuery("SELECT p FROM User p WHERE p.email = :emailer ", User.class);
		System.out.println("apurv quer");
		System.out.println(email);
		List<User> s=query.setParameter("emailer", email).getResultList();
		 
		if(s.isEmpty())
		 {
			 return null;
		 }
		 else
		 {
			 return s.get(0);	 
		 }
	
	}

	@Override
	public User findOneByUserNameOrEmail(String username, String email) {
		TypedQuery<User> query = em.createQuery("SELECT p FROM User p WHERE p.email = :emailer ", User.class);
		System.out.println("apurv quer");
		System.out.println(email);
		List<User> s=query.setParameter("emailer", email).getResultList();
		 
		return s.get(0);
	}

	@Override
	public User findOneByToken(String token) {
		//User user = em.find(User.class, token);
		TypedQuery<User> query = em.createQuery("SELECT p FROM User p WHERE p.activation_token = :token ", User.class);
		System.out.println("apurv quer");
		System.out.println(token);
		List<User> s=query.setParameter("token", token).getResultList();
		 if(s.isEmpty())
		 {
		 return null;
		 }
		 else
		 {
			 return s.get(0);
			 	 
		 }
	}

	@Override
	 @Modifying
	    @Transactional
	    @Query("update User u set u.email = :email, u.firstName = :firstName, "
	            + "u.lastName = :lastName, u.address = :address")
	public int updateUser(String email, String firstName, String lastName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	 @Modifying
	    @Transactional
	    @Query("update User u set u.lastLogin = CURRENT_TIMESTAMP where u.userName = ?1")
	public int updateLastLogin(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	  @Modifying
	    @Transactional
	    @Query("update User u set u.profilePicture = ?2 where u.userName = ?1")
	public int updateProfilePicture(String userName, String profilePicture) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateToken(User u) {
		User v = em.find(User.class, u.getUser_Id());
		 v.setActivation_token("1");
		return 0;
	}

}
