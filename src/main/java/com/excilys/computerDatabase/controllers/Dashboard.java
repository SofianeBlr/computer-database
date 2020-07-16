package com.excilys.computerDatabase.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.ComputerService;

@Controller()
@RequestMapping("/dashboard")
public class Dashboard{

	private static Logger logger = LoggerFactory.getLogger(Dashboard.class);

	@Autowired
	public ComputerService computerService;

	@GetMapping
	public String getDashboard(@RequestParam(required=false, name="numberPerPage" ,defaultValue = "10") String numberPerPageParam,
			@RequestParam(required=false, name="page",defaultValue = "1") String currentPageParam,
			@RequestParam(required=false, name="orderBy",defaultValue = "") String orderByPram,
			@RequestParam(required=false, name="search",defaultValue = "") String searchParam,
			Model model){
		int currentPage=1;
		int numberPerPage = 10;
		int navMaxPageIndex=5;
		int maxPage =0;
		Long numberOfComputer= 0L;
		String orderBy=null;

		if (!orderByPram.isEmpty()){
			orderBy=orderByPram;
		}
		if(!currentPageParam.isEmpty()) {		
			currentPage = Integer.parseInt(currentPageParam);
		}
		if(!numberPerPageParam.isEmpty()) {		
			numberPerPage = Integer.parseInt(numberPerPageParam);
		}

		if (currentPage < 1) {
			currentPage = 1;
		}
		List<ComputerDto> computerDtoPage = new ArrayList<ComputerDto>();
		if (searchParam.isEmpty()) {
			maxPage =computerService.getMaxPage(numberPerPage);
			if (currentPage>maxPage) {
				currentPage = maxPage;
			}
			numberOfComputer = computerService.size();
			List<Computer> computerPage = computerService.getPage(currentPage-1,numberPerPage,orderBy);
			for (Computer c : computerPage) {
				computerDtoPage.add(ComputerMapper.mapComputerDto(c));
			}
		}
		else {

			numberOfComputer = computerService.sizeWithSearch(searchParam);
			maxPage =computerService.getMaxPageWithSearch(numberPerPage, searchParam);
			if (currentPage>maxPage) {
				currentPage = maxPage;
			}
			List<Computer> computerPage = computerService.getPageWithSearch(currentPage-1,numberPerPage,searchParam,orderBy);
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
		Map<String,Object> attributeValues = new HashMap<String, Object>();
		attributeValues.put("numberOfComputer",numberOfComputer);
		attributeValues.put("computers", computerDtoPage);
		attributeValues.put("search", searchParam);
		attributeValues.put("maxPage", maxPage);
		attributeValues.put("currentPage", currentPage);
		attributeValues.put("navMaxPageIndex", navMaxPageIndex);
		attributeValues.put("numberPerPage", numberPerPage);
		attributeValues.put("orderBy", orderBy);		
		model.addAllAttributes(attributeValues);

		return "dashboard";
	}

	@PostMapping
	protected String postDashboard(@RequestParam(required=false, name="selection",defaultValue = "") String selection,
			@RequestParam(required=false, name="numberPerPage" ,defaultValue = "10") String numberPerPageParam,
			@RequestParam(required=false, name="page",defaultValue = "1") String currentPageParam,
			@RequestParam(required=false, name="orderBy",defaultValue = "") String orderByPram,
			@RequestParam(required=false, name="search",defaultValue = "") String searchParam,
			Model model) {
		if(!selection.isEmpty()) {
			String[] computerIds =selection.split(",");
			for(String c: computerIds) {
				try {
					computerService.delete(Long.parseLong(c));
				} catch (IllegalArgumentException e) {
					logger.error("Illegal arguments");
					logger.error("computer update not allowed",e);
				}
			}
		}
		return getDashboard(numberPerPageParam, currentPageParam, orderByPram, searchParam, model);
	}

}
