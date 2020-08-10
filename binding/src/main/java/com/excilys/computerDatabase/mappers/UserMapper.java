package com.excilys.computerDatabase.mappers;

import java.time.format.DateTimeParseException;

import com.excilys.computerDatabase.dtos.UserDto;
import com.excilys.computerDatabase.models.User;
import com.excilys.computerDatabase.validators.UserValidator;

public class UserMapper {
	
	public static UserDto mapUserDto(User user) {
		UserDto userDto= new UserDto(user);
		return userDto;
	}
	
	public static User toUser(UserDto userDto) throws IllegalArgumentException ,DateTimeParseException{
		User user = UserValidator.userValidator(userDto);
		return user;
	}

}
