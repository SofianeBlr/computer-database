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


@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(EditComputer.class);
	
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
		
		ComputerDto computerDto= new ComputerDto();
		Long computerId = null;
		if (request.getParameter("idComputer") != null) {
			try {
				computerId = Long.parseLong(request.getParameter("idComputer"));
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
		if(request.getAttribute("computerDto")==null) {
			request.setAttribute("computerDto", computerDto);
		}
		
		request.getRequestDispatcher("/views/editComputer.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDto computerDto= new ComputerDto();
		computerDto.setId("null");
		if (request.getParameter("id")!=null && !request.getParameter("id").isEmpty()) {
			computerDto.setId(request.getParameter("id"));
		}
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
			computerService.update(computer);
			request.setAttribute("computerDto", computerDto);
			request.setAttribute("sucess", "computer updated");
		} catch (DateTimeParseException e) {
			logger.error("invalid date format ");
			logger.error("computer update not allowed",e);
			request.setAttribute("error", "computer update not allowed");
		} catch (IllegalArgumentException e) {
			logger.error("Illegal arguments");
			logger.error("computer update not allowed",e);
			request.setAttribute("error", "computer update not allowed");
		}
		doGet(request, response);
	}
	

}
