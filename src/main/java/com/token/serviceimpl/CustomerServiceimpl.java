package com.token.serviceimpl;

import org.springframework.stereotype.Service;

import com.token.entity.Customer;
import com.token.repository.CustomerRepository;
import com.token.service.BasicService;
import com.token.service.CustomerService;

@Service
public class CustomerServiceimpl extends BasicService<Customer, CustomerRepository> implements CustomerService{

}