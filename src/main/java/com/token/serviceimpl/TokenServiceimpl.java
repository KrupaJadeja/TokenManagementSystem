package com.token.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.token.entity.Token;
import com.token.entity.User;
import com.token.payload.CallTokenRequest;
import com.token.repository.TokenRepository;
import com.token.service.BasicService;
import com.token.service.TokenService;


@Service
public class TokenServiceimpl extends BasicService<Token, TokenRepository> implements TokenService{

	@Override
	public List<Token> getTokeniestByPriority() {
		return repository.getTokeniestByPriority();
	}

	@Override
	public List<Token> getTokenForCall(String department) {
		return repository.getTokenForCall(department);
	}

	@Override
	public String editcallToken(CallTokenRequest callTokenRequest) {
		if(repository.getTokenForCall(callTokenRequest.getDepartment()).isEmpty()) {
			return "Error";
		}else {
			Token token = repository.getTokenForCall(callTokenRequest.getDepartment()).get(0);
			token.setCalled("Yes");
			token.setCounter(callTokenRequest.getCounter());
			token.setAssignedUser(callTokenRequest.getAssigned_user());
			token.setRecall(true);
			repository.save(token);
			return "Success";
		}
	}

	@Override
	public String deletebyDepartment(User user, String department, String counter) {
		if(repository.getTokenForStop(user, department, counter).isEmpty()) {
			return "Error";
		}else {
			repository.deletebyDepartment(user, department, counter);
			return "Success";
		}
	}
}