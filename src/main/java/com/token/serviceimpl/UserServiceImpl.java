package com.token.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.token.entity.User;
import com.token.exception.AppException;
import com.token.payload.ForgotPasswordRequest;
import com.token.payload.ResetPasswordRequest;
import com.token.payload.SignupRequest;
import com.token.repository.UserRepository;
import com.token.service.BasicService;
import com.token.service.EmailService;
import com.token.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl extends BasicService<User, UserRepository> implements UserService{

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${mail.fromname}")
	private String fromName;

	@Value("${mail.subject}")
	private String mailSubject;

	@Value("${mail.text}")
	private String mailText;

	@Autowired
	private EmailService emailService;

	@Override
	public void registerUser(@Valid SignupRequest signUpRequest) throws Exception {
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setUserName(signUpRequest.getUserName());
		user.setEmail(signUpRequest.getEmail());
		user.setRole(signUpRequest.getRole());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		repository.save(user);
	}

	@Override
	public boolean existsByUsername(String username) throws Exception {
		return repository.existsByUserName(username);
	}

	@Override
	public boolean existsByEmail(String email) throws Exception {
		return repository.existsByEmail(email);
	}

	@Override
	public List<User> getOnlyUsers() {
		return repository.getOnlyUsers();
	}

	@Override
	public void processForgotPassword(ForgotPasswordRequest useremail) throws Exception {
		User user = repository.findByEmail(useremail.getemail());
		String tokenStr = Jwts.builder()
				/*.setAudience(useremail.getemail())
				.setSubject(Long.toString(1))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();*/
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();
		user.setResetToken(tokenStr);
		repository.save(user);
		emailForgotPassword(useremail, tokenStr);

	}

	@Async
	private void emailForgotPassword(ForgotPasswordRequest useremail, String tokenStr) throws Exception {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(useremail.getemail());
		passwordResetEmail.setSubject(mailSubject);
		passwordResetEmail.setText(mailText + tokenStr);
		emailService.sendEmail(passwordResetEmail);	
	}

	@Override
	public void resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception {
		User user = repository.findByResetToken(resetPasswordRequest.getToken());
		if(null!=user){
			user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
			user.setResetToken("");
			repository.save(user);
			emailResetPassword(user.getEmail());
		}else{
			throw new AppException("User with token "+resetPasswordRequest.getToken()+" not found");
		}
	}

	@Async
	private void emailResetPassword(String useremail) throws Exception {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(useremail);
		passwordResetEmail.setSubject("Password reset successfully");
		passwordResetEmail.setText("Your password reset successfully..");
		emailService.sendEmail(passwordResetEmail);
	}
}