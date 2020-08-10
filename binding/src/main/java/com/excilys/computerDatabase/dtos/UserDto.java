package com.excilys.computerDatabase.dtos;

import com.excilys.computerDatabase.models.User;

public class UserDto {
	private String id;
	private String username;
	private String roleName;
	private String roleId;
	
	public UserDto(User user) {
		id= user.getId().toString();
		username= user.getUsername();
		roleName = user.getRoleName();
		roleId = user.getRoleId().toString();
	}
	
	public UserDto() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
