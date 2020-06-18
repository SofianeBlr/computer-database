package com.excilys.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.model.Company;


public abstract class CompanyMapper {

	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Company company=new Company(resultset.getInt("id"),resultset.getString("name"));
		return company;
	}
}