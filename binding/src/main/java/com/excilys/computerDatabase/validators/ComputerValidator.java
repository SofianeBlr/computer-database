package com.excilys.computerDatabase.validators;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Computer.ComputerBuilder;

public class ComputerValidator {

	public static Computer computerValidator(ComputerDto computerDto)
			throws DateTimeParseException, IllegalArgumentException {

		if (computerDto.getName() == null || computerDto.getName().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		ComputerBuilder cb = new ComputerBuilder()
				.setNameBuild(computerDto.getName())
				.setIdBuild(Long.parseLong(computerDto.getId()));
				

		if (computerDto.getIntroduced() != null && !computerDto.getIntroduced().isEmpty()) {
			cb.setIntroducedBuild(LocalDate.parse(computerDto.getIntroduced()));
			
		}
		if (computerDto.getDiscontinued() != null && !computerDto.getDiscontinued().isEmpty()) {
			cb.setDiscontinuedBuild(LocalDate.parse(computerDto.getDiscontinued()));
		}
		if (computerDto.getCompany().getId() != null && Long.parseLong(computerDto.getCompany().getId()) != 0L
				&& !computerDto.getCompany().getId().isEmpty()) {
			cb.setCompanyBuild(new Company.CompanyBuilder()
					.setIdBuild(Long.parseLong(computerDto.getCompany().getId()))
					.setNameBuild(computerDto.getCompany().getName())
					.build());
					
		} else {
			cb.setCompanyBuild(null);
		}
		return cb.build();
	}
	
	

}
