package com.excilys.computerDatabase.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;


public class ComputerMapperTest {

	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";

	private final Long idComputer = 10L;
	private final String computerName = "computer name";
	private final Date introduced = Date.valueOf(LocalDate.of( 2014 , 2 , 11 ));
	private final Date discontinued = Date.valueOf(LocalDate.of( 2020 , 4 , 2 ));
	private final Long idCompany = 15L;

	private ResultSet resultSet = Mockito.mock(ResultSet.class);

	@Test
	public void testMapper() {
		Computer computer = new Computer();
		try {
			Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
			Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
			Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
			Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
			Mockito.when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
			computer = ComputerMapper.mapComputer(resultSet);
		} catch (SQLException e) {
			fail("sql exception :" + e.getMessage());
		}

		Computer expComputer = new Computer(idComputer,computerName,introduced.toLocalDate(),discontinued.toLocalDate(),idCompany);


		assertEquals(expComputer, computer);
	}

	@Test
	public void testMapIntroducedNull() {
		Computer computer = new Computer();
		try {
			Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
			Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
			Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
			Mockito.when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
			computer = ComputerMapper.mapComputer(resultSet);
		} catch (SQLException e) {
			fail("sql exception :" + e.getMessage());
		}
		Computer expComputer = new Computer(idComputer,computerName,null,discontinued.toLocalDate(),idCompany);

		assertEquals(expComputer, computer);
	}

	@Test
	public void testMapdiscontinuedNull() {
		Computer computer = new Computer();
		try {
			Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
			Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
			Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
			Mockito.when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
			computer = ComputerMapper.mapComputer(resultSet);
		} catch (SQLException e) {
			fail("sql exception :" + e.getMessage());
		}
		Computer expComputer = new Computer(idComputer,computerName,introduced.toLocalDate(),idCompany);
		assertEquals(expComputer, computer);
	}

	@Test
	public void testMapComputerIdNull() {
		Computer computer = new Computer();
		try {
			Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
			Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
			Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
			Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
			computer = ComputerMapper.mapComputer(resultSet);
		} catch (SQLException e) {
			fail("sql exception :" + e.getMessage());
		}

		Computer expComputer = new Computer(idComputer,computerName,introduced.toLocalDate(),discontinued.toLocalDate());

		assertEquals(expComputer, computer);
	}
	
	@Test
	public void testMapComputerToComputerDto() {		

		Computer computer = new Computer(idComputer,computerName,introduced.toLocalDate(),discontinued.toLocalDate());
		ComputerDto computerDto = ComputerMapper.mapComputerDto(computer);
		assertEquals(computerDto.getId(), computer.getId().toString());
		assertEquals(computerDto.getName(), computer.getName());
		assertEquals(computerDto.getIntroduced(), computer.getIntroduced().toString());
		assertEquals(computerDto.getDiscontinued(), computer.getDiscontinued().toString());

	}
	
	@Test
	public void testMapComputerDtoToComputer() {		

		Computer computer = new Computer(idComputer,computerName,introduced.toLocalDate(),discontinued.toLocalDate(),10L);
		ComputerDto computerDto = ComputerMapper.mapComputerDto(computer);
		Computer fromDto = ComputerMapper.toComputer(computerDto);
		assertEquals(fromDto, computer);

	}
	

}
