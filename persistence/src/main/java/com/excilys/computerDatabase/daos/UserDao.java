package com.excilys.computerDatabase.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	public User getUserbyUserName(String userName){
		
		QUser user= QUser.user;
		JPAQuery<User> query = new JPAQuery<User>(entityManager);

		User ui = query.from(user)
				.where(user.username.eq(userName))
				.fetchOne();
		
		return ui;
	}
	
	public User createUser(User user) {
		entityManager.persist(user);
		return user;
	}

}
