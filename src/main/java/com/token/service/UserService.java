package com.token.service;

import java.util.List;

import com.token.entity.User;
import com.token.payload.ForgotPasswordRequest;
import com.token.payload.ResetPasswordRequest;
import com.token.payload.SignupRequest;

public interface UserService extends IFinder<User> , IService<User>{

	void registerUser(SignupRequest signUpRequest) throws Exception;
	
	boolean existsByUsername(String username) throws Exception;

	boolean existsByEmail(String email) throws Exception;
	
	List<User> getOnlyUsers();
	
	void processForgotPassword(ForgotPasswordRequest useremail) throws Exception;
	
	void resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception;

}