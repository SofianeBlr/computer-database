package com.excilys.computerDatabase.service;



import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.dao.DAO;
import com.excilys.computerDatabase.model.Company;

public class CompanyService extends ServiceDao<Company> {
	
	private static CompanyService companyService;

	public CompanyService(DAO<Company> dao) {
		super(dao);
	}
	
	public static synchronized CompanyService getInstance() {
		if(companyService == null) {
			companyService = new CompanyService(CompanyDao.getInstance());
		}
		return companyService;
	}



}
