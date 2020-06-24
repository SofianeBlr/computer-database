package com.excilys.computerDatabase.services;



import com.excilys.computerDatabase.daos.CompanyDao;
import com.excilys.computerDatabase.daos.DAO;
import com.excilys.computerDatabase.models.Company;

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
