package com.token.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.entity.User;
import com.token.exception.AppException;
import com.token.payload.UpdateUserPasswordRequest;
import com.token.security.JwtTokenProvider;
import com.token.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	HttpServletRequest req;

	@GetMapping("/getOnlyUsres")
	public List<User> getOnlyUsers(){
		return userService.getOnlyUsers();
	} 

	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/find")
	public ResponseEntity<User> findByToken() {
		String token = req.getHeader("Authorization").substring(7, req.getHeader("Authorization").length());
		return userService.findById(tokenProvider.getUserIdFromJWT(token))
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping()
	public Page<User> findAll(Pageable pageable) {
		return userService.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") Long id) {
		return userService.findById(id)
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping()
	public User save(@RequestBody User user) {
		return userService.save(user);
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody User web) throws Exception {
		try {
			
			User entity = userService.findById(web.getId()).orElse(null);
			if (entity == null) {
				return new ResponseEntity<AppException>(new AppException("User not found"), HttpStatus.BAD_REQUEST);
			}
			entity.setName(null!=web.getName()?web.getName():entity.getName());
			entity.setUserName(null!=web.getUserName()?web.getUserName():entity.getUserName());
			entity.setRole(null!=web.getUserName()?web.getRole():entity.getRole());
			entity.setPassword(null!=web.getPassword()? passwordEncoder.encode(web.getPassword()):entity.getPassword());
			entity.setEmail(null!=web.getEmail()?web.getEmail():entity.getEmail());
			userService.save(entity);
			return new ResponseEntity<String>("User updated successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<AppException>(new AppException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<User> db = userService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		userService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping("/changepassword")
	public ResponseEntity<?> changepassword(@RequestBody UpdateUserPasswordRequest passwordRequest) throws Exception{
		try {
			User entity = userService.findById(passwordRequest.getUser().getId()).orElse(null);
			if (entity == null) {
				return new ResponseEntity<AppException>(new AppException("User not found"), HttpStatus.BAD_REQUEST);
			}
			entity.setPassword(null!=passwordRequest.getNewpassword()? passwordEncoder.encode(passwordRequest.getNewpassword()):"");
			userService.save(entity);
			return new ResponseEntity<String>("User password updated successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<AppException>(new AppException(e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
