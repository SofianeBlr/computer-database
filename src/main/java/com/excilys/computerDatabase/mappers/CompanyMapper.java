package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.models.Company;


public abstract class CompanyMapper {

	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Long id = Long.valueOf(resultset.getLong("id"));
		Company company=new Company(id,resultset.getString("name"));
		return company;
	}
}