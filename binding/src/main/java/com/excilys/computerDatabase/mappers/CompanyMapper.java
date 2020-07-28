package com.excilys.computerDatabase.mappers;



import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.models.Company;


public class CompanyMapper{
	
	
	public static CompanyDto mapCompanyDto(Company company) {
		
		CompanyDto companyDto= new CompanyDto(company);
		return companyDto;
	}
}