package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.models.Company;


public abstract class CompanyMapper {
	private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Long id = Long.valueOf(resultset.getLong(ATTRIBUT_ID_COMPANY));
		Company company=new Company(id,resultset.getString(ATTRIBUT_NAME));
		return company;
	}
	public static CompanyDto mapCompanyDto(Company company) {
		
		CompanyDto companyDto= new CompanyDto(company);
		return companyDto;
	}
}