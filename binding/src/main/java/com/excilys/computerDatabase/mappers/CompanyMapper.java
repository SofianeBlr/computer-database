package com.excilys.computerDatabase.mappers;



import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.models.Company;


public class CompanyMapper{
	
	
	public static CompanyDto mapCompanyDto(Company company) {
		
		CompanyDto companyDto= new CompanyDto(company);
		return companyDto;
	}
	public static Company toCompany(CompanyDto companyDto) {
		if (companyDto.getName() == null || companyDto.getName().isEmpty()) {
			throw new IllegalArgumentException();
		}
		Company company= new Company();
		company.setId(Long.valueOf(companyDto.getId()));
		company.setName(companyDto.getName());
		return company;
		
	}
}