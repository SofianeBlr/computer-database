package com.excilys.computerDatabase.daos;



import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.models.QCompany;
import com.excilys.computerDatabase.models.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ComputerDao extends DAO<Computer> {



	@Override
	public ArrayList<Computer> getAll() {
		
		
		QComputer computer = QComputer.computer;
		
		JPAQuery<Computer>  query = new JPAQuery<Computer> (entityManager);	
		return  (ArrayList<Computer>)  query.from(computer).fetch();

	}

	@Override
	@Transactional
	public Computer create(Computer obj) {
		obj.setId(null);
		try {
			entityManager.persist(obj);
			return obj;

		}catch (Exception dae) {
			logger.error("Not able to add computer",dae);
			return null;
		}	

	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		try {
			queryFactory.delete(computer).where(computer.id.eq(id)).execute();
			return true;

		}catch (Exception dae) {
			logger.error("Not able to delete computer",dae);
			return false;
		}
	}

	@Override
	@Transactional
	public Computer update(Computer obj) {
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		try {
			queryFactory.update(computer)
			.where(computer.id.eq(obj.getId()))
			.set(computer.name, obj.getName())
			.set(computer.introduced, obj.getIntroduced())
			.set(computer.discontinued, obj.getDiscontinued())
			.set(computer.company.id, obj.getCompanyId())
			.execute();
			return obj;

		}catch (Exception dae) {
			logger.error("Not able to update computer",dae);
			return null;
		}	
	}

	@Override
	public Computer find(Long id) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);
		try {
			return query.from(computer).leftJoin(company).on(computer.company.id.eq(company.id))
					.where(computer.id.eq(id)).fetchOne();
		}catch (Exception dae) {
			logger.error("Not able to find computer",dae);
			return null;
		}	

	}

	@Override
	public Long maxId()  {
		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
			return query.select(computer.id.max()).from(computer).fetchOne();
		}catch (Exception dae) {
			logger.error("Not able to get maxId",dae);
			return 0L;
		}
	}

	@Override
	public Long size() {
		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
			return query.from(computer).fetchCount();
		}catch (Exception dae) {
			logger.error("Not able to get size",dae);
			return 0L;
		}
	}

	@Override
	public ArrayList<Computer> getPage(Page page) {
		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
		return  (ArrayList<Computer>) query.from(computer)
				.offset(page.getPage()*page.getNumberPerPage())
				.orderBy(orderByConversionQ(page.getOrderBy()).nullsLast())
				.limit(page.getNumberPerPage())
				.fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get page",e);
			return new ArrayList<Computer>();
		}
		
	}

	@Override
	public ArrayList<Computer> getPageWithSearch(Page page) {

		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
		return  (ArrayList<Computer>) query.from(computer).leftJoin(company).on(computer.company.id.eq(company.id))
				.where(computer.name.contains(page.getSearch()).or(company.name.contains(page.getSearch())))
					.offset(page.getPage()*page.getNumberPerPage())
					.orderBy(orderByConversionQ(page.getOrderBy()).nullsLast())
					.limit(page.getNumberPerPage())
					.fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get page",e);
			return new ArrayList<Computer>();
		}
	}


	@Override
	public Long sizeWithSearch(String search) {

		QCompany company = QCompany.company;
		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
		return query.from(computer).leftJoin(company).on(computer.company.id.eq(company.id))
				.where(computer.name.contains(search).or(company.name.contains(search)))
					.fetchCount();
		}
		catch (Exception e) {
			logger.error("Not able to get size with search",e);
			return 0L;
		}
		
	}


	
	private OrderSpecifier<?> orderByConversionQ(String order) {
		if(order==null ||order.length()!=5) {
			return QComputer.computer.id.asc();
		}
		if(order.substring(0,2).equals("cn")) {
			if(order.substring(2,5).equals("ASC")) {
				return QComputer.computer.name.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QComputer.computer.name.desc();
			}
		}
		else if(order.substring(0,2).equals("di")) {
			if(order.substring(2,5).equals("ASC")) {
				return QComputer.computer.introduced.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QComputer.computer.introduced.desc();
			}
		}
		else if(order.substring(0,2).equals("dd")) {
			if(order.substring(2,5).equals("ASC")) {
				return QComputer.computer.discontinued.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QComputer.computer.discontinued.desc();
			}
		}
		else if(order.substring(0,2).equals("ci")) {
			if(order.substring(2,5).equals("ASC")) {
				return QComputer.computer.company.id.asc();
			}
			else if(order.substring(2,5).equals("DSC")) {
				return QComputer.computer.company.id.desc();
			}
		}
		return QComputer.computer.id.asc();
	}
}
