package com.excilys.computerDatabase.Dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerDatabase.Model.Computer;

public class ComputerDao extends DAO<Computer> {

	@Override
	public ArrayList<Computer> getAll() {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		Statement myStmt;
		try {
			myStmt = connect.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from computer");
			
			while(myRs.next()) {
				Computer computer = new Computer(myRs.getInt("id"),myRs.getString("name"),myRs.getInt("company_id"));
				if(myRs.getDate("introduced") != null) {
					computer.setIntroduced(myRs.getDate("introduced").toLocalDate());
				}
				if(myRs.getDate("discontinued") != null) {
					computer.setIntroduced(myRs.getDate("discontinued").toLocalDate());
				}
				computers.add(computer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return computers;
	}

	@Override
	public boolean create(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Computer find(int id) {
		// TODO Auto-generated method stub
		Computer comp = null;
		Statement myStmt;
		try {
			myStmt = connect.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from company");
			myRs.next();
			comp = new Computer(myRs.getInt("id"),myRs.getString("name"),myRs.getInt("company_id"));
			if(myRs.getDate("introduced") != null) {
				comp.setIntroduced(myRs.getDate("introduced").toLocalDate());
			}
			if(myRs.getDate("discontinued") != null) {
				comp.setIntroduced(myRs.getDate("discontinued").toLocalDate());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return comp;
	}

}
