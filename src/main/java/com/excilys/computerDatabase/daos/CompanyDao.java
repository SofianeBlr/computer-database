package com.excilys.computerDatabase.daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.models.Company;

public class CompanyDao extends DAO<Company> {
	
	
	private final static String INSERT = "INSERT INTO company(name) VALUES(?);";
	private final static String DELETE = "DELETE FROM company WHERE id = ?;";
	private final static String UPDATE = "UPDATE company SET name = ? WHERE id = ?;";
	private final static String FINDALL = "select * from company";
	private final static String FIND = "select * from company where id=";
	private final static String MAXID = "select MAX(id) from company ";
	private final static String SIZE = "select count(*) from company";
	private final static String GET_PAGE = "select * from company LIMIT ?,?";
	
    private static CompanyDao companyDao;
	
    
    /**
     * Instance of the singleton CompanyDao.
     * @return the instance of CompanyDao
     */
    public static synchronized CompanyDao getInstance() {
        if (companyDao == null) {
            companyDao = new CompanyDao();
        }
        return companyDao;
    }



	
	@Override
	public ArrayList<Company> getAll() {
		ArrayList<Company> comps = new ArrayList<Company>();
		try (Connection connect = getConnection();
				Statement myStmt = connect.createStatement();
				ResultSet myRs= myStmt.executeQuery(FINDALL)){
			while(myRs.next()) {
				comps.add(CompanyMapper.mapCompany(myRs));
			}
		} catch (SQLException e) {
			logger.error("error get all companies");
		}
		
		return comps;
	}
	
	@Override
	public Company create(Company obj) {
		try (Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(INSERT)){
            preparedStatement.setString(1, obj.getName());
            preparedStatement.executeUpdate();
            return find(maxId());
        } catch (SQLException e) {
        	logger.error("error in create");
        }
		return null;
	}

	@Override
	public boolean delete(Company obj) {
		try (Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE)){
            preparedStatement.setLong(1, obj.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	logger.error("error in delete");
        }
		return false;
	}

	@Override
	public Company update(Company obj) {
		try (Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(UPDATE)){
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setLong(2, obj.getId());
            preparedStatement.executeUpdate();
            return find(obj.getId());
        } catch (SQLException e) {
        	logger.error("error in update");

        }
		return null;
	}

	@Override
	public Company find(Long id) {
		Company company = null;
		try (Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(FIND + id);){
			myRs.next();
			company= CompanyMapper.mapCompany(myRs);
		} catch (SQLException e) {
        	logger.error("error in find(id");
		}
		
		return company;
	}

	@Override
	public Long maxId() throws SQLException{
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(MAXID);) {
			myRs.next();
			return myRs.getLong("MAX(id)");
		}
	}
	@Override
	public Long size() {
		try (Connection connect = getConnection();
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
	public ArrayList<Company> getPage(int debut, int number) {
		ArrayList<Company> comps = new ArrayList<Company>();
		try(Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(GET_PAGE);) {
	        preparedStatement.setInt(1, debut);
	        preparedStatement.setInt(2, number);
			ResultSet myRs = preparedStatement.executeQuery();
			while(myRs.next()) {
				comps.add(CompanyMapper.mapCompany(myRs));
			}
			close(myRs);
		} catch (SQLException e) {
			logger.error("error in getPage()");		
			}
		
		return comps;
	}

	

}
