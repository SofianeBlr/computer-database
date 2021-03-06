package com.excilys.computerDatabase.restControllers;


import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.excilys.computerDatabase.dtos.ComputerDto;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.services.ComputerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("computer")
public class ComputerRestController {


	public ComputerService computerService;
	@Autowired
	public ComputerRestController(ComputerService computerService) {
		this.computerService=computerService;
	}

	
	
	@GetMapping(value = { "/page" }, produces = "application/json")
	public List<ComputerDto> listComputersPage(@RequestParam(required=false, name="numberPerPage" ,defaultValue = "10") Integer numberPerPageParam,
			@RequestParam(required=false, name="page",defaultValue = "1") Integer currentPageParam,
			@RequestParam(required=false, name="orderBy",defaultValue = "") String orderByPram,
			@RequestParam(required=false, name="search",defaultValue = "") String searchParam) { 

		if (currentPageParam < 1) {
			currentPageParam = 1;
		}
		Page page= new Page(currentPageParam-1,numberPerPageParam,searchParam.isEmpty()?null:searchParam,orderByPram.isEmpty()?null:orderByPram);
		List<ComputerDto> computerDtoPage = new ArrayList<ComputerDto>();
		List<Computer> computerPage;
		if (searchParam.isEmpty()) {
			computerPage = computerService.getPage(page);
		}
		else {
			computerPage = computerService.getPageWithSearch(page);
		}
		for (Computer c : computerPage) {
			computerDtoPage.add(ComputerMapper.mapComputerDto(c));
		}
		return computerDtoPage;
	}

	@GetMapping(value = { "/number" }, produces = "application/json")
	public Long numberComputers(@RequestParam(required=false, name="search",defaultValue = "") String searchParam) {
		if(searchParam.isEmpty()) {
			return computerService.size();
		}
		else {
			return computerService.sizeWithSearch(searchParam);
		}
	}






	@GetMapping(value ="/{id}", produces = "application/json")
	public ResponseEntity<ComputerDto> getComputer(@PathVariable Long id) {
		Computer computer= computerService.find(id);
		if(computer!=null) {
			return ResponseEntity.ok(ComputerMapper.mapComputerDto(computer));

		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ComputerDto());

		}
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deleteComputer(@PathVariable Long id) {
		if(computerService.delete(id)) {
			return ResponseEntity.ok("{\"sucess\":\"computer deleted\"}");

		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"The Computer is not found is the database\"}");
		}
	}
	@PostMapping(value = "/deleteList", produces = "application/json")
	public ResponseEntity<Map<String,String>> deleteCompanies(@RequestBody Map<String, List<Long>> map) {
		for(Long id:map.get("ids")) {
			computerService.delete(id);
		}
		Map<String,String> res = new HashMap<>();
		res.put("success", "computers deleted");
		return ResponseEntity.ok(res);
	}

	@PostMapping(value = { "", "/" }, produces = "application/json")
	public ResponseEntity<String> createComputer(@RequestBody ComputerDto dto) {
		dto.setId("0");
		if(dto.getCompanyId()==null) {
			dto.setCompanyId("0");
		}
		try {
		Computer computer=computerService.create(ComputerMapper.toComputer(dto));
		if(computer!=null){
			return ResponseEntity.ok("{\"id\": "+computer.getId()+"}");
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
	public ResponseEntity<String> updateComputer(@RequestBody ComputerDto dto) {
		try {
			if(computerService.update(ComputerMapper.toComputer(dto))!=null) {
				return ResponseEntity.ok("{\"sucess\" : \"computer updated\"}");

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"The Computer is not found is the database\"}");
			}
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"IllegalArgumentException\"}");
		}catch (DateTimeParseException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"DateTimeParseException\"}");
		}
	}
}
