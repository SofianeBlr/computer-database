package com.excilys.computerDatabase.servlets;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.ComputerService;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(Dashboard.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computerService = ComputerService.getInstance();

		int currentPage=1;
		int numberPerPage =10;
		String search = "";
		int navMaxPageIndex=5;
		int maxPage =0;
		Long numberOfComputer= 0L;

		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("numberPerPage") != null) {
			numberPerPage = Integer.parseInt(request.getParameter("numberPerPage"));
			request.setAttribute("numberPerPage", numberPerPage);
		}
		if (currentPage < 1) {
			currentPage = 1;
		}
		
		List<ComputerDto> computerDtoPage = new ArrayList<ComputerDto>();
		if (request.getParameter("search") == null||request.getParameter("search").isEmpty()) {
			maxPage =computerService.getMaxPage(numberPerPage);
			if (currentPage>maxPage) {
				currentPage = maxPage;
			}
			numberOfComputer = computerService.size();
			List<Computer> computerPage = computerService.getPage(currentPage-1,numberPerPage);
			for (Computer c : computerPage) {
				computerDtoPage.add(ComputerMapper.mapComputerDto(c));
			}
		}
		else {
			search =request.getParameter("search");
			numberOfComputer = computerService.sizeWithSearch(search);
			maxPage =computerService.getMaxPageWithSearch(numberPerPage, search);
			if (currentPage>maxPage) {
				currentPage = maxPage;
			}
			List<Computer> computerPage = computerService.getPage(currentPage-1,numberPerPage,search);
			for (Computer c : computerPage) {
				computerDtoPage.add(ComputerMapper.mapComputerDto(c));
			}

		}
	
		if(currentPage>2){
			navMaxPageIndex =currentPage+2;
		}
		if (maxPage<navMaxPageIndex) {
			navMaxPageIndex=maxPage;
		}
		request.setAttribute("numberOfComputer",numberOfComputer);
		request.setAttribute("computers", computerDtoPage);
		request.setAttribute("search", search);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("navMaxPageIndex", navMaxPageIndex);
		request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("selection")!=null) {
			ComputerService computerService = ComputerService.getInstance();
			String[] computerIds =request.getParameter("selection").split(",");
			for(String c: computerIds) {
				try {
					computerService.delete(Long.parseLong(c));
				} catch (IllegalArgumentException e) {
					logger.error("Illegal arguments");
					logger.error("computer update not allowed",e);
				}
			}
		}
		doGet(request, response);
	}
}
