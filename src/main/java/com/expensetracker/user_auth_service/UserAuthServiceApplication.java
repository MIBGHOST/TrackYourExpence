package com.expensetracker.user_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableMongoAuditing
public class  UserAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthServiceApplication.class, args);
	}

}
