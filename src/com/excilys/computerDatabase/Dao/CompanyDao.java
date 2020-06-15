package com.excilys.computerDatabase.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerDatabase.Model.Company;

public class CompanyDao extends DAO<Company> {
	
	
	@Override
	public ArrayList<Company> getAll() {
		// TODO Auto-generated method stub
		ArrayList<Company> comps = new ArrayList<Company>();
		Statement myStmt;
		try {
			myStmt = connect.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from company");
			while(myRs.next()) {
				comps.add(new Company(myRs.getInt("id"),myRs.getString("name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return comps;
	}
	
	@Override
	public boolean create(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Company find(int id) {
		// TODO Auto-generated method stub
		Company company = null;
		Statement myStmt;
		try {
			myStmt = connect.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from company");
			myRs.next();
			company=new Company(myRs.getInt("id"),myRs.getString("name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return company;
	}

	

}
