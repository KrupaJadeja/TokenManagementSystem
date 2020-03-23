package com.token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAutoConfiguration 
@SpringBootApplication
@EnableAsync
public class TokenManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenManagementSystemApplication.class, args);
	}

}
