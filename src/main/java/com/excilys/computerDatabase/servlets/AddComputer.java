package com.excilys.computerDatabase.servlets;

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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.CompanyService;
import com.excilys.computerDatabase.services.ComputerService;


@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
   	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Company> allCompanies = companyService.getAll();
		List<CompanyDto> companyDtos = new ArrayList<CompanyDto>();
		for (Company c : allCompanies) {
			companyDtos.add(CompanyMapper.mapCompanyDto(c));
		}

		request.setAttribute("companies", companyDtos);
		request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDto computerDto= new ComputerDto();
		computerDto.setId("0");
		if (request.getParameter("computerName")!=null && !request.getParameter("computerName").isEmpty()) {
			computerDto.setName(request.getParameter("computerName"));
		}
		if (request.getParameter("companyId")!=null &&!request.getParameter("companyId").isEmpty() && !request.getParameter("companyId").equals("0")) {
			CompanyDto company = new CompanyDto();
			company.setId(request.getParameter("companyId"));
			computerDto.setCompany(company);
		}
		if (request.getParameter("introduced")!=null && !request.getParameter("introduced").isEmpty()) {
			computerDto.setIntroduced(request.getParameter("introduced"));
		}
		if (request.getParameter("discontinued")!=null && !request.getParameter("discontinued").isEmpty()) {
			computerDto.setDiscontinued(request.getParameter("discontinued"));
		}
		try {
			Computer computer = ComputerMapper.toComputer(computerDto);
			computerService.create(computer);
		} catch (DateTimeParseException e) {
			logger.error("invalid date format ",e);
			logger.error("computer creation not allowed",e);
		} catch (IllegalArgumentException e) {
			logger.error("Illegal arguments",e);
			logger.error("computer creation not allowed",e);
		}
		doGet(request, response);
	}

}
