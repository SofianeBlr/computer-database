package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.validators.ComputerValidator;

public abstract class ComputerMapper {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";

	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getLong(ATTRIBUT_ID_COMPUTER),resultset.getString(ATTRIBUT_NAME));

		if(Long.valueOf(resultset.getLong(ATTRIBUT_COMPANY_ID))!=0L) {
			comp.setCompanyId(resultset.getLong(ATTRIBUT_COMPANY_ID));
		}
		if(resultset.getDate(ATTRIBUT_INTRODUCED) != null) {
			comp.setIntroduced(resultset.getDate(ATTRIBUT_INTRODUCED).toLocalDate());
		}
		if(resultset.getDate(ATTRIBUT_DISCONTINUED) != null) {
			comp.setDiscontinued(resultset.getDate(ATTRIBUT_DISCONTINUED).toLocalDate());
		}
		return comp;
	}
	public static Computer mapComputerWithCompany(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getLong(ATTRIBUT_ID_COMPUTER),resultset.getString(ATTRIBUT_NAME));
		
		comp.setCompanyName(resultset.getString("company_name"));

		comp.setCompanyName(resultset.getString("company_name"));
		if(Long.valueOf(resultset.getLong(ATTRIBUT_COMPANY_ID))!=0L) {
			comp.setCompanyId(resultset.getLong(ATTRIBUT_COMPANY_ID));
		}
		if(resultset.getDate(ATTRIBUT_INTRODUCED) != null) {
			comp.setIntroduced(resultset.getDate(ATTRIBUT_INTRODUCED).toLocalDate());
		}
		if(resultset.getDate(ATTRIBUT_DISCONTINUED) != null) {
			comp.setDiscontinued(resultset.getDate(ATTRIBUT_DISCONTINUED).toLocalDate());
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
