 	package com.excilys.computerDatabase.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.daos.CompanyDao;
import com.excilys.computerDatabase.models.Company;

@Service
public class CompanyService extends ServiceDao<Company> {
	
	@Autowired
	public CompanyService(CompanyDao companyDao) {
		super(companyDao);
	}


}
