package com.token.payload;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.token.entity.RoleName;

public class SignupRequest {

	private String name;
	private String userName;
	private String email;
	@Enumerated(EnumType.STRING)
	private RoleName role;
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RoleName getRole() {
		return role;
	}
	public void setRole(RoleName role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}