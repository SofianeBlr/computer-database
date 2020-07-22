package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.validators.ComputerValidator;

public class ComputerMapper implements RowMapper<Computer> {

	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";
	private static final String ATTRIBUT_COMPANY_NAME = "company_name";


	public static Computer mapComputer(ResultSet resultSet) throws SQLException {
		Computer comp = new Computer(resultSet.getLong(ATTRIBUT_ID_COMPUTER),resultSet.getString(ATTRIBUT_NAME));

		if(Long.valueOf(resultSet.getLong(ATTRIBUT_COMPANY_ID))!=0L) {
			comp.setCompanyId(resultSet.getLong(ATTRIBUT_COMPANY_ID));
		}
		if(resultSet.getDate(ATTRIBUT_INTRODUCED) != null) {
			comp.setIntroduced(resultSet.getDate(ATTRIBUT_INTRODUCED).toLocalDate());
		}
		if(resultSet.getDate(ATTRIBUT_DISCONTINUED) != null) {
			comp.setDiscontinued(resultSet.getDate(ATTRIBUT_DISCONTINUED).toLocalDate());
		}
		return comp;
	}

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Computer comp = new Computer(resultSet.getLong(ATTRIBUT_ID_COMPUTER),resultSet.getString(ATTRIBUT_NAME));

		comp.setCompanyName(resultSet.getString(ATTRIBUT_COMPANY_NAME));

		if(Long.valueOf(resultSet.getLong(ATTRIBUT_COMPANY_ID))!=0L) {
			comp.setCompanyId(resultSet.getLong(ATTRIBUT_COMPANY_ID));
		}
		if(resultSet.getDate(ATTRIBUT_INTRODUCED) != null) {
			comp.setIntroduced(resultSet.getDate(ATTRIBUT_INTRODUCED).toLocalDate());
		}
		if(resultSet.getDate(ATTRIBUT_DISCONTINUED) != null) {
			comp.setDiscontinued(resultSet.getDate(ATTRIBUT_DISCONTINUED).toLocalDate());
		}
		return comp;
	};
	public static ComputerDto mapComputerDto(Computer computer) {

		ComputerDto computerDto= new ComputerDto(computer);
		return computerDto;
	}

	public static Computer toComputer(ComputerDto computerDto) throws IllegalArgumentException ,DateTimeParseException{
		Computer computer = ComputerValidator.computerValidator(computerDto);
		return computer;
	}

}
