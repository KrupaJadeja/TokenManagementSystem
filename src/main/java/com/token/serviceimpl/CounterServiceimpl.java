package com.token.serviceimpl;

import org.springframework.stereotype.Service;

import com.token.entity.Counter;
import com.token.entity.User;
import com.token.repository.CounterRepository;
import com.token.repository.UserRepository;
import com.token.service.BasicService;
import com.token.service.CounterService;
import com.token.service.UserService;

@Service
public class CounterServiceimpl extends BasicService<Counter, CounterRepository> implements CounterService{

}
