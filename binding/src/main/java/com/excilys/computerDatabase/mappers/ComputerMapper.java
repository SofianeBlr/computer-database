package com.excilys.computerDatabase.mappers;

import java.time.format.DateTimeParseException;


import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.validators.ComputerValidator;

public class ComputerMapper {

	public static ComputerDto mapComputerDto(Computer computer) {

		ComputerDto computerDto= new ComputerDto(computer);
		return computerDto;
	}

	public static Computer toComputer(ComputerDto computerDto) throws IllegalArgumentException ,DateTimeParseException{
		Computer computer = ComputerValidator.computerValidator(computerDto);
		return computer;
	}

}
