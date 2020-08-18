package com.excilys.computerDatabase.daos;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.models.QCompany;
import com.excilys.computerDatabase.models.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CompanyDao extends DAO<Company> {


	@Override
	public ArrayList<Company> getAll() {

		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company> (entityManager);	
		
		try {
			return  (ArrayList<Company>)  query.from(company).orderBy(company.name.asc().nullsLast()).fetch();
		}catch (Exception dae) {
			logger.error("Not able to get all companies",dae);
			return new ArrayList<Company>();
		}
	}

	@Override
	@Transactional
	public Company create(Company obj) {
		
		obj.setId(null);
		try {
			entityManager.persist(obj);
			return obj;

		}catch (DataAccessException dae) {
			logger.error("Not able to add company",dae);
			return null;
		}	

	}

	@Transactional
	@Override
	public boolean delete(Long id) {
		QCompany company = QCompany.company;
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		try {
			queryFactory.delete(computer).where(company.id.eq(id)).execute();
			queryFactory.delete(company).where(company.id.eq(id)).execute();
			return true;

		}catch (Exception dae) {
			logger.error("Not able to delete computer",dae);
			return false;
		}
	}

	@Override
	@Transactional
	public Company update(Company obj) {
		QCompany company = QCompany.company;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		try {
			queryFactory.update(company)
			.where(company.id.eq(obj.getId()))
			.set(company.name, obj.getName())
			.execute();
			return obj;

		}catch (Exception dae) {
			logger.error("Not able to update computer",dae);
			return null;
		}
	}

	@Override
	public Company find(Long id) {
		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company>(entityManager);
		try {
			return query.from(company)
					.where(company.id.eq(id)).fetchOne();
		}catch (Exception dae) {
			logger.error("Not able to find computer",dae);
			return null;
		}	
	}

	@Override
	public Long maxId() throws SQLException{
		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company>(entityManager);	
		try {
			return query.select(company.id.max()).from(company).fetchOne();
		}catch (Exception dae) {
			logger.error("Not able to get maxId",dae);
			return 0L;
		}
	}
	@Override
	public Long size() {
		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company>(entityManager);
		try {
			return query.from(company).fetchCount();
		}catch (Exception dae) {
			logger.error("Not able to get size",dae);
			return 0L;
		}
	}

	@Override
	public ArrayList<Company> getPage(Page page) {
		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company>(entityManager);	
		try {
		return  (ArrayList<Company>) query.from(company)
				.offset(page.getPage()*page.getNumberPerPage())
				.orderBy(orderByConversionQ(page.getOrderBy()).nullsLast())
				.limit(page.getNumberPerPage())
				.fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get page",e);
			return new ArrayList<Company>();
		}
	}




	@Override
	public ArrayList<Company> getPageWithSearch(Page page) {
		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company>(entityManager);	
		try {
		return (ArrayList<Company>) query.from(company).where(company.name.contains(page.getSearch()))
				.offset(page.getPage()*page.getNumberPerPage())
				.orderBy(orderByConversionQ(page.getOrderBy()).nullsLast())
				.limit(page.getNumberPerPage())
				.fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get size with search",e);
			return new ArrayList<Company>();
		}
	}




	@Override
	public Long sizeWithSearch(String search) {
		QCompany company = QCompany.company;
		JPAQuery<Company>  query = new JPAQuery<Company>(entityManager);	
		try {
		return query.from(company).where(company.name.contains(search))
					.fetchCount();
		}
		catch (Exception e) {
			logger.error("Not able to get size with search",e);
			return 0L;
		}
	}
	
	private OrderSpecifier<?> orderByConversionQ(String order) {
		if(order==null) {
			return QCompany.company.id.asc();
		}
		if(order.substring(0,2).equals("cn")) {
			if(order.substring(2,5).equals("ASC")) {
				return QCompany.company.name.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QCompany.company.name.desc();
			}
		}
		else if(order.substring(0,2).equals("ci")) {
			if(order.substring(2,5).equals("ASC")) {
				return QCompany.company.id.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QCompany.company.id.desc();
			}
		}
		return QCompany.company.id.asc();
	}

}
