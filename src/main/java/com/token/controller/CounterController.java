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

import com.token.entity.Counter;
import com.token.service.CounterService;

@RestController
@RequestMapping("/counter")
@CrossOrigin("*")
public class CounterController {

	@Autowired
	private CounterService counterService;
	
	@GetMapping("/findAll")
	public List<Counter> findAll() {
		return counterService.findAll();
	}
	
	@GetMapping()
	public Page<Counter> findAll(Pageable pageable) {
		return counterService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Counter> findById(@PathVariable("id") Long id) {
		return counterService.findById(id)
				.map(counter -> ResponseEntity.ok().body(counter))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Counter save(@RequestBody Counter counter) {
		return counterService.save(counter);
	}
	
	@PutMapping()
	public Counter update(@RequestBody Counter counter) {
		return counterService.save(counter);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Counter> db = counterService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		counterService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
