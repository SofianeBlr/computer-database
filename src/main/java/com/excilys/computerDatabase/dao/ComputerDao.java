package com.excilys.computerDatabase.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.mapper.ComputerMapper;


public class ComputerDao extends DAO<Computer> {
	
	private final static String INSERT = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private final static String DELETE = "DELETE FROM computer WHERE id = ?;";
	private final static String UPDATE = "UPDATE computer SET name = ?, introduced = ? , discontinued = ? , company_id = ? WHERE id = ?;";
	private final static String FINDALL = "select * from computer";
	private final static String FIND = "select * from computer where id=";
	private final static String MAXID = "select MAX(id) from computer ";
	private final static String SIZE = "select count(*) from computer";
	private final static String GET_PAGE = "select * from computer LIMIT ?,?";
	


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
			//e.printStackTrace();
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
            if( obj.getCompanyId()!=0) {
            	preparedStatement.setInt(4,obj.getCompanyId());
            }
            else {
            	preparedStatement.setNull(4, Types.BIGINT);
            }

            preparedStatement.executeUpdate();
            return find(maxId());
        } catch (SQLException e) {
            //e.printStackTrace();
        }
		return null;
	}

	@Override
	public boolean delete(Computer obj) {
		try (Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE)){
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
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
            if( obj.getCompanyId()!=0) {
            	preparedStatement.setInt(4,obj.getCompanyId());
            }
            else {
            	preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setInt(5,obj.getId());

            preparedStatement.executeUpdate();
            return find(obj.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public Computer find(int id) {
		Computer comp = null;
		
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(FIND + id);) { 
			
			myRs.next();
			comp =  ComputerMapper.mapComputer(myRs);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		
		return comp;
	}
	
	@Override
	public int maxId() throws SQLException  {
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(MAXID);) {
			myRs.next();
			return myRs.getInt("MAX(id)");
		}
	}

	@Override
	public int size() {
		try(Connection connect = getConnection();
				Statement myStmt= connect.createStatement();
				ResultSet myRs = myStmt.executeQuery(SIZE);) {
			myRs.next();
			return myRs.getInt("count(*)");
		} catch (SQLException e) {
			//e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public ArrayList<Computer> getPage(int debut, int number) {
        ArrayList<Computer> computers = new ArrayList<Computer>();
        ResultSet myRs;
        
		try(Connection connect = getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(GET_PAGE);) {

			preparedStatement.setInt(1, debut);
	        preparedStatement.setInt(2, number); 
			myRs = preparedStatement.executeQuery();
			
			while(myRs.next()) {
				Computer computer = ComputerMapper.mapComputer(myRs);
				computers.add(computer);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computers;
	}

}