package com.excilys.computerDatabase.validators;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.models.Computer;

public class ComputerValidator {


	public static Computer computerValidator(ComputerDto computerDto) throws DateTimeParseException,IllegalArgumentException {

		if(computerDto.getName() == null || computerDto.getName().isEmpty() ) {
			throw new IllegalArgumentException();
		}
		Computer comp = new Computer(Long.parseLong(computerDto.getId()),computerDto.getName());
		if (computerDto.getIntroduced()!=null && !computerDto.getIntroduced().isEmpty()) {
			comp.setIntroduced(LocalDate.parse(computerDto.getIntroduced()));
		}
		if (computerDto.getDiscontinued()!=null&& !computerDto.getDiscontinued().isEmpty()) {
			comp.setDiscontinued(LocalDate.parse(computerDto.getDiscontinued()));
		}
		if (computerDto.getCompany().getId()!=null&&Long.parseLong(computerDto.getCompany().getId())!=0L&& !computerDto.getCompany().getId().isEmpty()) {
			comp.setCompanyId(Long.parseLong(computerDto.getCompany().getId()));
		}
		return comp;
	}

}
