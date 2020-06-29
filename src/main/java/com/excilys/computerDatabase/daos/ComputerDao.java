package com.excilys.computerDatabase.daos;


import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;


public class ComputerDao extends DAO<Computer> {

	private final static String INSERT = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private final static String DELETE = "DELETE FROM computer WHERE id = ?;";
	private final static String UPDATE = "UPDATE computer SET name = ?, introduced = ? , discontinued = ? , company_id = ? WHERE id = ?;";
	private final static String FINDALL = "select * from computer";
	private final static String FIND = "select * from computer where id=";
	private final static String MAXID = "select MAX(id) from computer ";
	private final static String SIZE = "select count(*) from computer";
	//private final static String GET_PAGE = "select * from computer LIMIT ?,?";
	private final static String GET_PAGE_W_COMPANY = "select computer.id,computer.name,introduced,discontinued,company_id,cp.name as company_name"
			+ " from computer LEFT JOIN company as cp on computer.company_id = cp.id "
			+ "Order By id LIMIT ?,?";
	private static String getPageWithSearch(String search) {
		return "select computer.id,computer.name,introduced,discontinued,company_id,cp.name as company_name"
				+ " from computer LEFT JOIN company as cp on computer.company_id = cp.id "
				+ "where computer.name like '%"+search + "%' Order By id LIMIT ?,?";
	}
	private static String getMaxWithSearch(String search) {
		return "select count(id)"
				+ " from computer"
				+ " where name like '%"+search + "%';";
	}
		
			

	private static ComputerDao computerDao;


	/**
	 * Instance of the singleton ComputerDao.
	 * @return the instance of ComputerDao
	 */
	public static synchronized ComputerDao getInstance() {
		if (computerDao == null) {
			computerDao = new ComputerDao();
		}
		return computerDao;
	}


	@Override
	public ArrayList<Computer> getAll() {
		ArrayList<Computer> computers = new ArrayList<Computer>();


		try(Connection connect = getConnection();
				Statement myStmt = connect.createStatement();
				ResultSet myRs= myStmt.executeQuery(FINDALL)) {
			while(myRs.next()) {
				Computer computer = ComputerMapper.mapComputer(myRs);
				computers.add(computer);
			}
		} catch (SQLException e) {
			logger.error("error in getAll()");
			e.printStackTrace();
		}


		return computers;
	}

	@Override
	public Computer create(Computer obj) {
		try (Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(INSERT)){



			preparedStatement.setString(1, obj.getName());
			if( obj.getIntroduced()!=null) {
				preparedStatement.setDate(2, Date.valueOf(obj.getIntroduced()));
			}
			else {
				preparedStatement.setNull(2, Types.DATE);;
			}
			if( obj.getDiscontinued()!=null) {
				preparedStatement.setDate(3, Date.valueOf(obj.getDiscontinued()));
			}
			else {
				preparedStatement.setNull(3, Types.DATE);
			}
			if( obj.getCompanyId()!=null) {
				preparedStatement.setLong(4,obj.getCompanyId());
			}
			else {
				preparedStatement.setNull(4, Types.BIGINT);
			}

			preparedStatement.executeUpdate();
			return find(maxId());
		} catch (SQLException e) {
			logger.error("error in create()");
		}
		return null;
	}

	@Override
	public boolean delete(Computer obj) {
		try (Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE)){
			preparedStatement.setLong(1, obj.getId());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("error in delete()");
		}
		return false;
	}

	@Override
	public Computer update(Computer obj) {
		try(Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(UPDATE)) {

			preparedStatement.setString(1, obj.getName());
			if( obj.getIntroduced()!=null) {
				preparedStatement.setDate(2, Date.valueOf(obj.getIntroduced()));
			}
			else {
				preparedStatement.setNull(2, Types.DATE);
			}
			if( obj.getDiscontinued()!=null) {
				preparedStatement.setDate(3, Date.valueOf(obj.getDiscontinued()));
			}
			else {
				preparedStatement.setNull(3, Types.DATE);
			}
			if( obj.getCompanyId()!=null) {
				preparedStatement.setLong(4,obj.getCompanyId());
			}
			else {
				preparedStatement.setNull(4, Types.BIGINT);
			}
			preparedStatement.setLong(5,obj.getId());

			preparedStatement.executeUpdate();
			return find(obj.getId());
		} catch (SQLException e) {
			logger.error("error in update()");
		}
		return null;
	}

	@Override
	public Computer find(Long id) {
		Computer comp = null;

		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(FIND + id);) { 

			myRs.next();
			comp =  ComputerMapper.mapComputer(myRs);
		} catch (SQLException e) {
			logger.error("error in fin(id)");
		}

		return comp;
	}

	@Override
	public Long maxId() throws SQLException  {
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(MAXID);) {
			myRs.next();
			return myRs.getLong("MAX(id)");
		}
	}

	@Override
	public Long size() {
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(SIZE);) {
			myRs.next();
			return myRs.getLong("count(*)");
		} catch (SQLException e) {
			logger.error("error in size()");
			return null;
		}

	}

	@Override
	public ArrayList<Computer> getPage(int debut, int number) {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ResultSet myRs;

		try(Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(GET_PAGE_W_COMPANY);) {

			preparedStatement.setInt(1, debut);
			preparedStatement.setInt(2, number); 
			myRs = preparedStatement.executeQuery();

			while(myRs.next()) {
				Computer computer = ComputerMapper.mapComputerWithCompany(myRs);
				computers.add(computer);
			}


		} catch (SQLException e) {
			logger.error("error in getPage()");
		}

		return computers;
	}
	
	@Override
	public ArrayList<Computer> getPageWithSearch(int debut, int number,String search) {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ResultSet myRs;

		try(Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(getPageWithSearch(search));) {

			preparedStatement.setInt(1, debut);
			preparedStatement.setInt(2, number);
			myRs = preparedStatement.executeQuery();

			while(myRs.next()) {
				Computer computer = ComputerMapper.mapComputerWithCompany(myRs);
				computers.add(computer);
			}


		} catch (SQLException e) {
			logger.error("error in getPage()");
			e.printStackTrace();
		}

		return computers;
	}


	@Override
	public Long sizeWithSearch(String search) {
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(getMaxWithSearch(search));) {
			myRs.next();
			return myRs.getLong("count(id)");
		} catch (SQLException e) {
			logger.error("error in size()");
			e.printStackTrace();
			return 0l;
		}
	}

}
