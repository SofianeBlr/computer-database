package com.excilys.computerDatabase.restControllers;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.dtos.UserDto;
import com.excilys.computerDatabase.mappers.UserMapper;
import com.excilys.computerDatabase.models.User;
import com.excilys.computerDatabase.models.Page;
import com.excilys.computerDatabase.services.UserService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("user")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = { "/page" }, produces = "application/json")
	public List<UserDto> listUsersPage(@RequestParam(required=false, name="numberPerPage" ,defaultValue = "10") Integer numberPerPageParam,
			@RequestParam(required=false, name="page",defaultValue = "1") Integer currentPageParam,
			@RequestParam(required=false, name="orderBy",defaultValue = "") String orderByPram,
			@RequestParam(required=false, name="search",defaultValue = "") String searchParam) { 

		if (currentPageParam < 1) {
			currentPageParam = 1;
		}
		Page page= new Page(currentPageParam-1,numberPerPageParam,searchParam.isEmpty()?null:searchParam,orderByPram.isEmpty()?null:orderByPram);
		List<UserDto> userDtoPage = new ArrayList<UserDto>();
		List<User> userPage = new ArrayList<User>();
		if (searchParam.isEmpty()) {
			userPage = userService.getPage(page);
		}
		else {
			userPage = userService.getPageWithSearch(page);
		}
		for (User c : userPage) {
			userDtoPage.add(UserMapper.mapUserDto(c));
		}
		return userDtoPage;
	}

	@GetMapping(value = { "/number" }, produces = "application/json")
	public Long numberUsers(@RequestParam(required=false, name="search",defaultValue = "") String searchParam) {
		if(searchParam.isEmpty()) {
			return userService.size();
		}
		else {
			return userService.sizeWithSearch(searchParam);
		}
	}






	@GetMapping(value ="/{id}", produces = "application/json")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
		User user= userService.find(id);
		if(user!=null) {
			return ResponseEntity.ok(UserMapper.mapUserDto(user));

		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserDto());

		}
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		if(userService.delete(id)) {
			return ResponseEntity.ok("{\"sucess\" : \"user deleted\"}");

		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"The User is not found is the database\"}");
		}
	}

	

	@PutMapping(value = { "", "/" }, produces = "application/json")
	public ResponseEntity<String> updateRoleUser(@RequestBody UserDto dto) {
		try {
			if(userService.update(UserMapper.toUser(dto))!=null) {
				return ResponseEntity.ok("{\"sucess\" : \"role updated\"}");

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"The User is not found is the database\"}");
			}
		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"IllegalArgumentException\"}");
		}catch (DateTimeParseException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\" : \"DateTimeParseException\"}");
		}
	}
	
	
}
