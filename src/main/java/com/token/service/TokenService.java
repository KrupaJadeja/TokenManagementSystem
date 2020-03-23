package com.token.service;

import java.util.List;

import com.token.entity.Token;
import com.token.entity.User;
import com.token.payload.CallTokenRequest;

public interface TokenService extends IFinder<Token> , IService<Token>{
	
	List<Token> getTokeniestByPriority();
	
	List<Token> getTokenForCall(String department);
	
	String editcallToken(CallTokenRequest callTokenRequest);
	
	String deletebyDepartment(User user,String department,String counter);
}