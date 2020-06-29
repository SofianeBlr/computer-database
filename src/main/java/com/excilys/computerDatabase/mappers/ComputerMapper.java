package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.validators.ComputerValidator;

public abstract class ComputerMapper {

	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getLong("id"),resultset.getString("name"));

		if(Long.valueOf(resultset.getLong("company_id"))!=0L) {
			comp.setCompanyId(resultset.getLong("company_id"));
		}
		if(resultset.getDate("introduced") != null) {
			comp.setIntroduced(resultset.getDate("introduced").toLocalDate());
		}
		if(resultset.getDate("discontinued") != null) {
			comp.setDiscontinued(resultset.getDate("discontinued").toLocalDate());
		}
		return comp;
	}
	public static Computer mapComputerWithCompany(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getLong("id"),resultset.getString("name"));
		
		comp.setCompanyName(resultset.getString("company_name"));

		comp.setCompanyName(resultset.getString("company_name"));
		if(Long.valueOf(resultset.getLong("company_id"))!=0L) {
			comp.setCompanyId(resultset.getLong("company_id"));
		}
		if(resultset.getDate("introduced") != null) {
			comp.setIntroduced(resultset.getDate("introduced").toLocalDate());
		}
		if(resultset.getDate("discontinued") != null) {
			comp.setDiscontinued(resultset.getDate("discontinued").toLocalDate());
		}
		return comp;
	}
	public static ComputerDto mapComputerDto(Computer computer) {
		
		ComputerDto computerDto= new ComputerDto(computer);
		return computerDto;
	}
	
	public static Computer toComputer(ComputerDto computerDto) throws IllegalArgumentException ,DateTimeParseException{
		Computer computer = ComputerValidator.computerValidator(computerDto);
		return computer;
	}

}
