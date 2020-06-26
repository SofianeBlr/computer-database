package com.excilys.computerDatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.ComputerService;

import dtos.ComputerDto;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computerService = ComputerService.getInstance();

		int currentPage=1;
		int numberPerPage =10;
		String search = "";
		int navMaxPageIndex=5;

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
		int maxPage =computerService.getMaxPage(numberPerPage);
		List<ComputerDto> computerDtoPage = new ArrayList<ComputerDto>();
		if (request.getParameter("search") == null||request.getParameter("search").isEmpty()) {
			if (currentPage>maxPage) {
				currentPage = maxPage;
			}
			List<Computer> computerPage = computerService.getPage(currentPage-1,numberPerPage);
			for (Computer c : computerPage) {
				computerDtoPage.add(ComputerMapper.mapComputerDto(c));
			}
		}
		else {
			search =request.getParameter("search");
			maxPage =computerService.getMaxPageWithSearch(numberPerPage, search);
			if (currentPage>maxPage) {
				currentPage = maxPage;
			}
			List<Computer> computerPage = computerService.getPage(currentPage-1,numberPerPage,search);
			for (Computer c : computerPage) {
				computerDtoPage.add(ComputerMapper.mapComputerDto(c));
			}

		}
		
		if(currentPage>2) {
			if((currentPage+2)<maxPage){
				navMaxPageIndex =currentPage+2;
			}
			else {
				navMaxPageIndex=maxPage;
			}
		}
		Long numberOfComputer = computerService.size();
		request.setAttribute("numberOfComputer",numberOfComputer);
		request.setAttribute("computers", computerDtoPage);
		request.setAttribute("search", search);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("navMaxPageIndex", navMaxPageIndex);
		request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
}
