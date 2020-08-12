package com.excilys.computerDatabase.restControllers;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.services.CompanyService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("company")
public class CompanyRestController {
	public CompanyService companyService;
	@Autowired
	public CompanyRestController(CompanyService companyService) {
		this.companyService=companyService;
	}
	@GetMapping(value = { "/page" }, produces = "application/json")
	public List<CompanyDto> listCompanyPage(@RequestParam(required=false, name="numberPerPage" ,defaultValue = "10") Integer numberPerPageParam,
			@RequestParam(required=false, name="page",defaultValue = "1") Integer currentPageParam,
			@RequestParam(required=false, name="orderBy",defaultValue = "") String orderByPram,
			@RequestParam(required=false, name="search",defaultValue = "") String searchParam) { 
		
		if (currentPageParam < 1) {
			currentPageParam = 1;
		}
		Page page= new Page(currentPageParam-1,numberPerPageParam,searchParam.isEmpty()?null:searchParam,orderByPram.isEmpty()?null:orderByPram);

		List<CompanyDto> companyDtoPage = new ArrayList<CompanyDto>();
		List<Company> companyPage;
		if (searchParam.isEmpty()) {
			companyPage = companyService.getPage(page);
		}
		else {
			companyPage = companyService.getPageWithSearch(page);
		}
		for (Company c : companyPage) {
			companyDtoPage.add(CompanyMapper.mapCompanyDto(c));
		}
		return companyDtoPage;
	}
	
	@GetMapping(value = { "/getAll" }, produces = "application/json")
	public ArrayList<Company> getAll() {
			return companyService.getAll();
	}

	@GetMapping(value = { "/number" }, produces = "application/json")
	public Long numberCompany(@RequestParam(required=false, name="search",defaultValue = "") String searchParam) {
		if(searchParam.isEmpty()) {
			return companyService.size();
		}
		return companyService.sizeWithSearch(searchParam);
	}
	@GetMapping(value ="/{id}", produces = "application/json")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
		Company company= companyService.find(id);
		if(company!=null) {
			return ResponseEntity.ok(CompanyMapper.mapCompanyDto(company));

		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CompanyDto());

		}
	}
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
		if(companyService.delete(id)) {
			return ResponseEntity.ok("{sucess:deleted}");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Company not found\"}");
		}
	}
	@PostMapping(value = { "", "/" }, produces = "application/json")
	public ResponseEntity<String> createCompany(@RequestBody CompanyDto dto) {
		dto.setId("0");
		try {
		Company company=companyService.create(CompanyMapper.toCompany(dto));
		if(company!=null){
			return ResponseEntity.ok("{\"id\": "+company.getId()+"}");
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"insertion failed\"}");
		}
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"IllegalArgumentException\"}");
		}catch (DateTimeParseException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"DateTimeParseException\"}");
		}
	}

	@PutMapping(value = { "", "/" }, produces = "application/json")
	public ResponseEntity<String> updateCompany(@RequestBody CompanyDto dto) {
		try {
			if(companyService.update(CompanyMapper.toCompany(dto))!=null) {
				return ResponseEntity.ok("{\"sucess\" : \"company updated\"}");

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"The Company is not found is the database\"}");
			}
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"IllegalArgumentException\"}");
		}catch (DateTimeParseException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"DateTimeParseException\"}");
		}
	}
}
