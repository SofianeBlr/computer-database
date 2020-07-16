package com.excilys.computerDatabase.controllers;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.CompanyService;
import com.excilys.computerDatabase.services.ComputerService;


@Controller()
@RequestMapping("/editComputer")
public class EditComputer{

	private static Logger logger = LoggerFactory.getLogger(EditComputer.class);
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@GetMapping
	public String getEditComputer(@RequestParam(required=false, name="idComputer",defaultValue = "") String idComputer,
			Model model){
		
		List<Company> allCompanies = companyService.getAll();
		List<CompanyDto> companyDtos = new ArrayList<CompanyDto>();
		for (Company c : allCompanies) {
			companyDtos.add(CompanyMapper.mapCompanyDto(c));
		}
		model.addAttribute("companies", companyDtos);
		
		ComputerDto computerDto= new ComputerDto();
		Long computerId = null;
		if (!idComputer.isEmpty()) {
			try {
				computerId = Long.parseLong(idComputer);
			}
			catch (NumberFormatException e) {
				logger.error("NumberFormatException",e);
			}
		}
		if(computerId!=null) {
			Computer comp =computerService.find(computerId);
			if(comp!=null) {
				computerDto = ComputerMapper.mapComputerDto(comp);
			}
		}
		if(model.getAttribute("newComputerDto")==null) {
			model.addAttribute("newComputerDto", computerDto);
		}
		
		return "editComputer";

	}
	
	@PostMapping
	protected String postEditComputer(@ModelAttribute ComputerDto computerDto,
			Model model){
		try {
			Computer computer = ComputerMapper.toComputer(computerDto);
			computerService.update(computer);
			model.addAttribute("newComputerDto", computerDto);
			model.addAttribute("sucess", "computer updated");
		} catch (DateTimeParseException e) {
			logger.error("invalid date format ");
			logger.error("computer update not allowed",e);
			model.addAttribute("error", "computer update not allowed");
		} catch (IllegalArgumentException e) {
			logger.error("Illegal arguments");
			logger.error("computer update not allowed",e);
			model.addAttribute("error", "computer update not allowed");
		}
		return getEditComputer(computerDto.getId(), model);
	}
	
	
	@ModelAttribute
	public ComputerDto newComputerDto(@RequestParam(required=false, name="id",defaultValue = "") String id,
			@RequestParam(required =false, name="name") String computerName,
			@RequestParam(required=false, name="introduced") String introduced,
			@RequestParam(required=false, name="discontinued") String discontinued,
			@RequestParam(required=false, name="companyId") String companyId
			) {
		ComputerDto computerDto= new ComputerDto();
		computerDto.setId(id);
		computerDto.setName(computerName);
		computerDto.getCompany().setId(companyId);
		computerDto.setIntroduced(introduced);
		computerDto.setDiscontinued(discontinued);
		return computerDto;
	}
	

}
