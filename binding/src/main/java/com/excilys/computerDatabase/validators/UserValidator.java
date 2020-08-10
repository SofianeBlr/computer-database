package com.excilys.computerDatabase.validators;

import com.excilys.computerDatabase.dtos.UserDto;
import com.excilys.computerDatabase.models.User;

public class UserValidator {
	public static User userValidator(UserDto userDto) throws IllegalArgumentException {

		if(userDto.getId() == null || userDto.getId().isEmpty() ) {
			throw new IllegalArgumentException();
		}
		if(userDto.getRoleId() == null || userDto.getRoleId().isEmpty() ) {
			throw new IllegalArgumentException();
		}
		User user = new User();
		user.setId(Long.parseLong(userDto.getId()));
		user.setRoleId(Long.parseLong(userDto.getRoleId()));
		return user;
	}
}
