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

import com.token.entity.Customer;
import com.token.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/findAll")
	public List<Customer> findAll() {
		return customerService.findAll();
	}
	
	@GetMapping()
	public Page<Customer> findAll(Pageable pageable) {
		return customerService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
		return customerService.findById(id)
				.map(customer -> ResponseEntity.ok().body(customer))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Customer save(@RequestBody Customer customer) {
		return customerService.save(customer);
	}
	
	@PutMapping()
	public Customer update(@RequestBody Customer customer) {
		return customerService.save(customer);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Customer> db = customerService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		customerService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}