package com.token.payload;

import com.token.entity.User;

public class CallTokenRequest {
	private String department;
	
	private String counter;
	
	private User assigned_user;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public User getAssigned_user() {
		return assigned_user;
	}

	public void setAssigned_user(User assigned_user) {
		this.assigned_user = assigned_user;
	}
}
