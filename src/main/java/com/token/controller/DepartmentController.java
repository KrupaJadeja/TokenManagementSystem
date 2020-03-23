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

import com.token.entity.Department;
import com.token.service.DepartmentService;

@RestController
@RequestMapping("/department")
@CrossOrigin("*")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/findAll")
	public List<Department> findAll() {
		return departmentService.findAll();
	}
	
	@GetMapping()
	public Page<Department> findAll(Pageable pageable) {
		return departmentService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Department> findById(@PathVariable("id") Long id) {
		return departmentService.findById(id)
				.map(department -> ResponseEntity.ok().body(department))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Department save(@RequestBody Department department) {
		return departmentService.save(department);
	}
	
	@PutMapping()
	public Department update(@RequestBody Department department) {
		return departmentService.save(department);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Department> db = departmentService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		departmentService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
