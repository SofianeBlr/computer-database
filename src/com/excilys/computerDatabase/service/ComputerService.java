package com.excilys.computerDatabase.service;



import com.excilys.computerDatabase.dao.ComputerDao;
import com.excilys.computerDatabase.dao.DAO;
import com.excilys.computerDatabase.model.Computer;

public class ComputerService extends ServiceDao<Computer> {
	
	private static ComputerService computerService;

	public ComputerService(DAO<Computer> dao) {
		super(dao);
	}
	
	public static ComputerService getInstance() {
		if(computerService == null) {
			computerService = new ComputerService(new ComputerDao());
		}
		return computerService;
	}
	

}
