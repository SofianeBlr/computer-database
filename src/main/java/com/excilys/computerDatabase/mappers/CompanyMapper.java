package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.models.Company;


public class CompanyMapper implements RowMapper<Company>{
	private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Long id = Long.valueOf(resultset.getLong(ATTRIBUT_ID_COMPANY));
		Company company=new Company(id,resultset.getString(ATTRIBUT_NAME));
		return company;
	}
	
	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return mapCompany(resultSet);
	}
	
	
	public static CompanyDto mapCompanyDto(Company company) {
		
		CompanyDto companyDto= new CompanyDto(company);
		return companyDto;
	}
}