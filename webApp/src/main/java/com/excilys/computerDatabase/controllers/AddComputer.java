package com.excilys.computerDatabase.controllers;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.CompanyService;
import com.excilys.computerDatabase.services.ComputerService;



@Controller()
@RequestMapping("/addComputer")
public class AddComputer{
	private static Logger logger = LoggerFactory.getLogger(AddComputer.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerService computerService;

	@GetMapping
	public String getAddComputer(@ModelAttribute ComputerDto computerDto,Model model) {

		List<Company> allCompanies = companyService.getAll();
		List<CompanyDto> companyDtos = new ArrayList<CompanyDto>();
		for (Company c : allCompanies) {
			companyDtos.add(CompanyMapper.mapCompanyDto(c));
		}
		model.addAttribute("companies", companyDtos);
		return "addComputer";
	}


	@PostMapping
	public String postAddComputer(@ModelAttribute ComputerDto computerDto,
			Model model) {
		try {
			Computer computer = ComputerMapper.toComputer(computerDto);
			computerService.create(computer);
			logger.debug("Computer added : " + computer.getName());
		} catch (DateTimeParseException e) {
			logger.error("invalid date format ",e);
			logger.error("computer creation not allowed",e);
		} catch (IllegalArgumentException e) {
			logger.error("Illegal arguments",e);
			logger.error("computer creation not allowed",e);
		}
		return getAddComputer(computerDto,model);
	}
	
	@ModelAttribute
	public ComputerDto newComputerDto(@RequestParam(required=false,name="name") String computerName,
			@RequestParam(required=false, name="introduced") String introduced,
			@RequestParam(required=false, name="discontinued") String discontinued,
			@RequestParam(required=false, name="companyId") String companyId,
			Model model) {
		ComputerDto computerDto= new ComputerDto();
		computerDto.setId("0");
		computerDto.setName(computerName);
		computerDto.getCompany().setId(companyId);
		computerDto.setIntroduced(introduced);
		computerDto.setDiscontinued(discontinued);
		return computerDto;
	}

}
