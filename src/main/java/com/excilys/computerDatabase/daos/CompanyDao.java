package com.excilys.computerDatabase.daos;


import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Page;

@Repository
public class CompanyDao extends DAO<Company> {


	private final static String INSERT = "INSERT INTO company(name) VALUES(:name);";
	private final static String DELETE = "DELETE FROM company WHERE id = :id;";
	private final static String UPDATE = "UPDATE company SET name = :name WHERE id = :id;";
	private final static String FIND_ALL = "select id , name from company";
	private final static String FIND = "select id , name from company where id=:id";
	private final static String MAXID = "select MAX(id) from company ";
	private final static String SIZE = "select count(id) from company";
	private final static String GET_PAGE = "select id , name from company LIMIT :start,:number";
	private final static String DELETE_COMPANY_COMPUTERS = "DELETE FROM computer where company_id = :id";





	@Override
	public ArrayList<Company> getAll() {

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
		try {
			return (ArrayList<Company>) vJdbcTemplate.query(FIND_ALL,  new CompanyMapper());
		}catch (DataAccessException dae) {
			logger.error("Not able to get all companies",dae);
			return new ArrayList<Company>();
		}
	}

	@Override
	public Company create(Company obj) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();

		vParams.addValue("name", obj.getName(),Types.VARCHAR);

		try {
			vJdbcTemplate.update(INSERT,vParams);
			return obj;

		}catch (DataAccessException dae) {
			logger.error("Not able to add company",dae);
			return null;
		}	

	}

	@Transactional
	@Override
	public boolean delete(Long id) {
		try {
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("id", id,Types.BIGINT);
			vJdbcTemplate.update(DELETE_COMPANY_COMPUTERS, vParams);
			vJdbcTemplate.update(DELETE,vParams);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public Company update(Company obj) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", obj.getId());
		vParams.addValue("name", obj.getName(),Types.VARCHAR);
		try {
			vJdbcTemplate.update(UPDATE,vParams);
			return obj;

		}catch (DataAccessException dae) {
			logger.error("Not able to update computer",dae);
			return null;
		}	
	}

	@Override
	public Company find(Long id) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();

		vParams.addValue("id", id,Types.BIGINT);

		try {
			return vJdbcTemplate.queryForObject(FIND, vParams, new CompanyMapper());
		}catch (DataAccessException dae) {
			logger.error("Not able to find company",dae);
			return null;
		}
	}

	@Override
	public Long maxId() throws SQLException{
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
	public ArrayList<Company> getPage(Page page) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("number", page.getNumberPerPage());
		vParams.addValue("start", page.getPage()*page.getNumberPerPage());

		try {
			return (ArrayList<Company>) vJdbcTemplate.query(GET_PAGE,vParams,  new CompanyMapper());
		}catch (DataAccessException dae) {
			logger.error("Not able to get page",dae);
			return new ArrayList<Company>();
		}
	}




	@Override
	public ArrayList<Company> getPageWithSearch(Page page) {
		return null;
	}




	@Override
	public Long sizeWithSearch(String search) {
		return null;
	}


}
