package com.excilys.computerDatabase.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.daos.ComputerDao;
import com.excilys.computerDatabase.models.Computer;

@Service
public class ComputerService extends ServiceDao<Computer> {
	
	@Autowired
	public ComputerService(ComputerDao computerDao) {
		super(computerDao);		
	}
	
}
