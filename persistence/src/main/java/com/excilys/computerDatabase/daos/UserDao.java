package com.excilys.computerDatabase.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.computerDatabase.models.QUser;
import com.excilys.computerDatabase.models.User;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
@EnableTransactionManagement
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	private Long anonRole_id=4L;

	public User getUserbyUserName(String userName){
		
		QUser user= QUser.user;
		JPAQuery<User> query = new JPAQuery<User>(entityManager);

		User ui = query.from(user)
				.where(user.username.eq(userName))
				.fetchOne();
		
		return ui;
	}
	
	@Transactional
	public User createUser(User user) {
		user.setRoleId(anonRole_id);
		try{
			entityManager.persist(user);
		}catch (Exception e) {
			return null;
		}
		return user;
	}
	


}
