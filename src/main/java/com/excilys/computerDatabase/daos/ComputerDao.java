package com.excilys.computerDatabase.daos;


import java.sql.Date;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.models.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class ComputerDao extends DAO<Computer> {

	private final static String INSERT = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES(:name,:introduced,:discontinued,:companyId);";
	private final static String DELETE = "DELETE FROM computer WHERE id = :id;";
	private final static String UPDATE = "UPDATE computer SET name = :name, introduced = :introduced , discontinued = :discontinued , company_id = :companyId WHERE id = :id;";
	private final static String FIND_ALL = "select computer.id,computer.name,introduced,discontinued,company_id from computer";
	private final static String FIND = "select computer.id,computer.name,introduced,discontinued,company_id from computer where id=:id";
	private final static String MAXID = "select MAX(id) from computer ";
	private final static String SIZE = "select count(id) from computer";
	private final static String GET_PAGE_W_COMPANY = "select computer.id,computer.name,introduced,discontinued,company_id,cp.name as company_name"
			+ " from computer LEFT JOIN company as cp on computer.company_id = cp.id "
			+ "Order By %s LIMIT :start,:number";
	private final static String GET_PAGE_W_SEARCH = "select computer.id,computer.name,introduced,discontinued,company_id,cp.name as company_name"
			+ " from computer LEFT JOIN company as cp on computer.company_id = cp.id "
			+ "where computer.name like :search or cp.name like :search"
			+" Order By %s LIMIT :start,:number;";
	private final static String GET_MAX_PAGE_W_SEARCH = "select count(computer.id)" 
			+ " from computer LEFT JOIN company as cp on computer.company_id = cp.id "  
			+ "where computer.name like :search or cp.name like :search;";


	@Override
	public ArrayList<Computer> getAll() {
		
		
		QComputer computer = QComputer.computer;
		
		JPAQuery<Computer>  query = new JPAQuery<Computer> (entityManager);	
		List<Computer> test = query.from(computer).fetch();
		return  (ArrayList<Computer>) test;
		/*
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
		RowMapper<Computer> vRowMapper = new RowMapper<Computer>() {
			public Computer mapRow(ResultSet rs, int pRowNum) throws SQLException {
				return ComputerMapper.mapComputer(rs);
			}
		};
		try {
			return (ArrayList<Computer>) vJdbcTemplate.query(FIND_ALL,  vRowMapper);
		}catch (DataAccessException dae) {
			logger.error("Not able to get all computers",dae);
			return new ArrayList<Computer>();
		}*/
	}

	@Override
	public Computer create(Computer obj) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();

		Date introduced = obj.getIntroduced()!=null?Date.valueOf(obj.getIntroduced()):null;
		Date discontinued = obj.getDiscontinued()!=null?Date.valueOf(obj.getDiscontinued()):null;

		vParams.addValue("name", obj.getName(),Types.VARCHAR);
		vParams.addValue("introduced", introduced,Types.DATE);
		vParams.addValue("discontinued",discontinued ,Types.DATE);
		vParams.addValue("companyId", obj.getCompanyId(),Types.BIGINT);

		try {
			vJdbcTemplate.update(INSERT,vParams);
			return obj;

		}catch (DataAccessException dae) {
			logger.error("Not able to add computer",dae);
			return null;
		}	

	}

	@Override
	public boolean delete(Long id) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id,Types.BIGINT);

		try {
			vJdbcTemplate.update(DELETE,vParams);
			return true;

		}catch (DataAccessException dae) {
			logger.error("Not able to delete computer",dae);
			return false;
		}
	}

	@Override
	public Computer update(Computer obj) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();

		Date introduced = obj.getIntroduced()!=null?Date.valueOf(obj.getIntroduced()):null;
		Date discontinued = obj.getDiscontinued()!=null?Date.valueOf(obj.getDiscontinued()):null;


		vParams.addValue("id", obj.getId());
		vParams.addValue("name", obj.getName(),Types.VARCHAR);
		vParams.addValue("introduced",introduced,Types.DATE);
		vParams.addValue("discontinued", discontinued,Types.DATE);
		vParams.addValue("companyId", obj.getCompanyId(),Types.BIGINT);

		try {
			vJdbcTemplate.update(UPDATE,vParams);
			return obj;

		}catch (DataAccessException dae) {
			logger.error("Not able to update computer",dae);
			return null;
		}	
	}

	@Override
	public Computer find(Long id) {
		Computer comp = null;

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();

		vParams.addValue("id", id,Types.BIGINT);

		RowMapper<Computer> vRowMapper = new RowMapper<Computer>() {
			public Computer mapRow(ResultSet rs, int pRowNum) throws SQLException {
				return ComputerMapper.mapComputer(rs);
			}
		};
		try {
			comp =vJdbcTemplate.queryForObject(FIND, vParams, vRowMapper);
			return comp;
		}catch (DataAccessException dae) {
			logger.error("Not able to find computer",dae);
			return null;
		}	

	}

	@Override
	public Long maxId()  {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
		try {
			return vJdbcTemplate.queryForObject(MAXID, Long.class);
		}catch (DataAccessException dae) {
			logger.error("Not able to get maxId",dae);
			return 0L;
		}
	}

	@Override
	public Long size() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
		try {
			return vJdbcTemplate.queryForObject(SIZE, Long.class);
		}catch (DataAccessException dae) {
			logger.error("Not able to get size",dae);
			return 0L;
		}
	}

	@Override
	public ArrayList<Computer> getPage(Page page) {
		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
		return  (ArrayList<Computer>) query.from(computer).offset(page.getPage()*page.getNumberPerPage()).orderBy(orderByConversionQ(page.getOrderBy()).nullsLast()).limit(page.getNumberPerPage()).fetch();
		}
		catch (Exception e) {
			logger.error("Not able to get page",e);
			return new ArrayList<Computer>();
		}
		
	}

	@Override
	public ArrayList<Computer> getPageWithSearch(Page page) {

		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
		return  (ArrayList<Computer>) query.from(computer).where(computer.name.like("%" + page.getSearch()+ "%").or(computer.company.name.like("%" + page.getSearch()+ "%")))
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

		/*
		QComputer computer = QComputer.computer;
		JPAQuery<Computer>  query = new JPAQuery<Computer>(entityManager);	
		try {
		return query.from(computer).where(computer.name.like(search!=null?"%"+search+"%":"%%").or(computer.company.name.like(search!=null?"%"+search+"%":"%%")))
					.fetchCount();
		}
		catch (Exception e) {
			logger.error("Not able to get size with search",e);
			return 0L;
		}
		*/
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("search", search!=null?"%"+search+"%":"%%");
		try {
			return vJdbcTemplate.queryForObject(GET_MAX_PAGE_W_SEARCH, vParams, Long.class);
		}catch (DataAccessException dae) {
			logger.error("Not able to get size with search",dae);
			return 0L;
		}
	}


	private String orderByConversion(String order) {
		if(order==null ||order.length()!=5) {
			return null;
		}
		String converted ="";
		if(order.substring(0,2).equals("cn")) {
			converted += " computer.name ";
		}
		else if(order.substring(0,2).equals("di")) {
			converted += " computer.introduced ";
		}
		else if(order.substring(0,2).equals("dd")) {
			converted += " computer.discontinued ";
		}
		else if(order.substring(0,2).equals("ci")) {
			converted += " company_name ";
		}
		if(order.substring(2,5).equals("ASC")) {
			converted = converted + "IS NULL," + converted + " ASC";
		}
		else if(order.substring(2,5).equals("DSC")) {
			converted += "DESC";
		}
		return converted;
	}
	private OrderSpecifier orderByConversionQ(String order) {
		if(order==null ||order.length()!=5) {
			return QComputer.computer.id.asc();
		}
		String converted ="";
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
