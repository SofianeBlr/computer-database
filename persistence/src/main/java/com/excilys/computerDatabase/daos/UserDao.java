package com.excilys.computerDatabase.daos;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.computerDatabase.models.User;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.models.QRole;
import com.excilys.computerDatabase.models.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@EnableTransactionManagement
public class UserDao extends DAO<User>{

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
	public User create(User user) {
		user.setRoleId(anonRole_id);
		try{
			entityManager.persist(user);
		}catch (Exception e) {
			return null;
		}
		return user;
	}

	@Override
	public ArrayList<User> getAll() {
		QUser user = QUser.user;
		
		JPAQuery<User>  query = new JPAQuery<> (entityManager);	
		return  (ArrayList<User>)  query.from(user).fetch();
	}

	

	@Override
	@Transactional
	public boolean delete(Long id) {
		QUser user = QUser.user;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		try {
			queryFactory.delete(user).where(user.id.eq(id)).execute();
			return true;

		}catch (Exception dae) {
			logger.error("Not able to delete user",dae);
			return false;
		}
	}

	@Override
	@Transactional
	public User update(User obj) {
		QUser user = QUser.user;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		try {
			queryFactory.update(user)
			.where(user.id.eq(obj.getId()))
			.set(user.role.id, obj.getRoleId())
			.execute();
			return obj;

		}catch (Exception dae) {
			logger.error("Not able to update user",dae);
			return null;
		}	
	}
	
	@Transactional
	public boolean updatePassword(String username, String newPass) {
		QUser user = QUser.user;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		try {
			queryFactory.update(user)
			.where(user.username.eq(username))
			.set(user.password,newPass)
			.execute();
			return true;

		}catch (Exception dae) {
			logger.error("Not able to update user",dae);
			return false;
		}	
	}

	@Override
	public User find(Long id) {
		QUser user = QUser.user;
		QRole role = QRole.role;
		JPAQuery<User>  query = new JPAQuery<User>(entityManager);
		try {
			return query.from(user).leftJoin(role).on(user.role.id.eq(role.id))
					.where(user.id.eq(id)).fetchOne();
		}catch (Exception dae) {
			logger.error("Not able to find user",dae);
			return null;
		}	

	}

	@Override
	public Long maxId()  {
		QUser user = QUser.user;
		JPAQuery<User>  query = new JPAQuery<User>(entityManager);	
		try {
			return query.select(user.id.max()).from(user).fetchOne();
		}catch (Exception dae) {
			logger.error("Not able to get maxId",dae);
			return 0L;
		}
	}

	@Override
	public Long size() {
		QUser user = QUser.user;
		JPAQuery<User>  query = new JPAQuery<User>(entityManager);	
		try {
			return query.from(user).fetchCount();
		}catch (Exception dae) {
			logger.error("Not able to get size",dae);
			return 0L;
		}
	}

	@Override
	public ArrayList<User> getPage(Page page) {
		QUser user = QUser.user;
		JPAQuery<User>  query = new JPAQuery<User>(entityManager);	
		try {
		return  (ArrayList<User>) query.from(user)
				.offset(page.getPage()*page.getNumberPerPage())
				.orderBy(orderByConversionQ(page.getOrderBy()).nullsLast())
				.limit(page.getNumberPerPage())
				.fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get page",e);
			return new ArrayList<User>();
		}
		
	}

	@Override
	public ArrayList<User> getPageWithSearch(Page page) {

		QUser user = QUser.user;
		QRole role = QRole.role;
		JPAQuery<User>  query = new JPAQuery<User>(entityManager);	
		try {
		return  (ArrayList<User>) query.from(user).leftJoin(role).on(user.role.id.eq(role.id))
				.where(user.username.contains(page.getSearch()).or(role.roleName.contains(page.getSearch())))
					.offset(page.getPage()*page.getNumberPerPage())
					.orderBy(orderByConversionQ(page.getOrderBy()).nullsLast())
					.limit(page.getNumberPerPage())
					.fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get page",e);
			return new ArrayList<User>();
		}
	}


	@Override
	public Long sizeWithSearch(String search) {

		QUser user = QUser.user;
		QRole role = QRole.role;
		JPAQuery<User>  query = new JPAQuery<User>(entityManager);	
		try {
		return query.from(user).leftJoin(role).on(user.role.id.eq(role.id))
				.where(user.username.contains(search).or(role.roleName.contains(search)))
					.fetchCount();
		}
		catch (Exception e) {
			logger.error("Not able to get size with search",e);
			return 0L;
		}
		
	}
	private OrderSpecifier<?> orderByConversionQ(String order) {
		if(order==null ||order.length()!=5) {
			return QUser.user.id.asc();
		}
		if(order.substring(0,2).equals("cn")) {
			if(order.substring(2,5).equals("ASC")) {
				return QUser.user.username.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QUser.user.username.desc();
			}
		}
	
		else if(order.substring(0,2).equals("ri")) {
			if(order.substring(2,5).equals("ASC")) {
				return QUser.user.role.roleName.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QUser.user.role.roleName.desc();
			}
		}
		else if(order.substring(0,2).equals("ui")) {
			if(order.substring(2,5).equals("ASC")) {
				return QUser.user.id.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QUser.user.id.desc();
			}
		}
		return QUser.user.id.asc();
	}

	


}
