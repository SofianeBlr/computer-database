package com.excilys.computerDatabase.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;


public class ComputerMapperTest {

	private final Long idComputer = 10L;
	private final String computerName = "computer name";
	private final Date introduced = Date.valueOf(LocalDate.of( 2014 , 2 , 11 ));
	private final Date discontinued = Date.valueOf(LocalDate.of( 2020 , 4 , 2 ));
	private final Long idCompany = 15L;

	
	@Test
	public void testMapComputerToComputerDto() {		

		
		Computer computer = new Computer.ComputerBuilder()
				.setNameBuild("test")
				.setIdBuild(0L)		
				.setIntroducedBuild(introduced.toLocalDate())
				.setDiscontinuedBuild(discontinued.toLocalDate())
				.setCompanyBuild(new Company.CompanyBuilder().setIdBuild(10L).setNameBuild("testCompany").build())
				.build();
		
		ComputerDto computerDto = ComputerMapper.mapComputerDto(computer);
		
		assertEquals(computerDto.getId(), computer.getId().toString());
		assertEquals(computerDto.getName(), computer.getName());
		assertEquals(computerDto.getIntroduced(), computer.getIntroduced().toString());
		assertEquals(computerDto.getDiscontinued(), computer.getDiscontinued().toString());

	}
	
	@Test
	public void testMapComputerDtoToComputer() {		

		Computer computer = new Computer.ComputerBuilder()
				.setNameBuild("test")
				.setIdBuild(0L)		
				.setIntroducedBuild(introduced.toLocalDate())
				.setDiscontinuedBuild(discontinued.toLocalDate())
				.setCompanyBuild(new Company.CompanyBuilder().setIdBuild(10L)
						.setNameBuild("testCompany")
						.build())
				.build();
		
		ComputerDto computerDto = ComputerMapper.mapComputerDto(computer);
		
		Computer fromDto = ComputerMapper.toComputer(computerDto);
		assertEquals(fromDto, computer);

	}
	@Test
	public void testMapComputerDtoToComputerWithNull() {		

		Computer computer = new Computer.ComputerBuilder()
				.setNameBuild("test")
				.setIdBuild(0L)						
				.setDiscontinuedBuild(discontinued.toLocalDate())
				.build();
		ComputerDto computerDto = ComputerMapper.mapComputerDto(computer);
		Computer fromDto = ComputerMapper.toComputer(computerDto);
		assertEquals(fromDto, computer);

	}
	

}
