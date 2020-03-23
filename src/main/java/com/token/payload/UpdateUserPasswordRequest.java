package com.token.payload;

import com.token.entity.User;

public class UpdateUserPasswordRequest {
	private String newpassword;
	
	private User user;

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
