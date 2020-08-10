package com.excilys.computerDatabase.restControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.services.CompanyService;

@RestController
@CrossOrigin
@RequestMapping("company")
public class CompanyRestController {
	public CompanyService companyService;
	@Autowired
	public CompanyRestController(CompanyService companyService) {
		this.companyService=companyService;
	}
	@GetMapping(value = { "/page" }, produces = "application/json")
	public List<CompanyDto> listCompanyPage(@RequestParam(required=false, name="numberPerPage" ,defaultValue = "10") Integer numberPerPageParam,
			@RequestParam(required=false, name="page",defaultValue = "1") Integer currentPageParam) { 
		
		Page page= new Page(currentPageParam-1,numberPerPageParam,null,null);
		List<CompanyDto> computerDtoPage = new ArrayList<CompanyDto>();
		List<Company> computerPage = new ArrayList<Company>();
		computerPage = companyService.getPage(page);
		for (Company c : computerPage) {
			computerDtoPage.add(CompanyMapper.mapCompanyDto(c));
		}
		return computerDtoPage;
	}

	@GetMapping(value = { "/number" }, produces = "application/json")
	public Long numberCompany() {
			return companyService.size();
	}
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deleteComputer(@PathVariable Long id) {
		if(companyService.delete(id)) {
			return ResponseEntity.ok("{sucess:deleted}");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Company not found\"}");
		}
	}
}
