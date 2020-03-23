package com.token.serviceimpl;

import org.springframework.stereotype.Service;

import com.token.entity.Department;
import com.token.repository.DepartmentRepository;
import com.token.service.BasicService;
import com.token.service.DepartmentService;

@Service
public class DepartmentServiceimpl extends BasicService<Department, DepartmentRepository> implements DepartmentService{

}