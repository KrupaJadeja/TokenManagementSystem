package com.token.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.entity.Token;
import com.token.payload.CallTokenRequest;
import com.token.service.TokenService;

@RestController
@RequestMapping("/token")
@CrossOrigin("*")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/getByPriority")
	public List<Token> getByPriority() {
		return tokenService.getTokeniestByPriority();
	}
	
	@GetMapping("/findAll")
	public List<Token> findAll() {
		return tokenService.findAll();
	}
	
	@GetMapping()
	public Page<Token> findAll(Pageable pageable) {
		return tokenService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Token> findById(@PathVariable("id") Long id) {
		return tokenService.findById(id)
				.map(token -> ResponseEntity.ok().body(token))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Token save(@RequestBody Token token) {
		return tokenService.save(token);
	}
	
	@PutMapping()
	public Token update(@RequestBody Token token) {
		return tokenService.save(token);
	}
	
	@PutMapping("/call")
	public String editcallToken(@RequestBody CallTokenRequest callTokenRequest) {
		return tokenService.editcallToken(callTokenRequest);
	}
	
	@PostMapping("/stop")
	public String deleteByDepartment(@RequestBody CallTokenRequest callTokenRequest) {
		return tokenService.deletebyDepartment(callTokenRequest.getAssigned_user(), callTokenRequest.getDepartment(), callTokenRequest.getCounter());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Token> db = tokenService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		tokenService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}	
}