package com.excilys.computerDatabase.services;



import com.excilys.computerDatabase.daos.ComputerDao;
import com.excilys.computerDatabase.daos.DAO;
import com.excilys.computerDatabase.models.Computer;

public class ComputerService extends ServiceDao<Computer> {
	
	private static ComputerService computerService;

	public ComputerService(DAO<Computer> dao) {
		super(dao);
	}
	
	public static synchronized ComputerService getInstance() {
		if(computerService == null) {
			computerService = new ComputerService(ComputerDao.getInstance());
		}
		return computerService;
	}
	

}
