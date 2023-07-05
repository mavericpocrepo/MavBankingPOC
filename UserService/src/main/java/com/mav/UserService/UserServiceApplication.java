package com.mav.UserService;

import com.mav.UserService.model.Users;
import com.mav.UserService.repo.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadUserData(UsersRepository usersRepository) {
		return args -> {
			Users user1 = new Users();
			user1.setEmail("test@g.com");
			user1.setPassword("test");
			user1.setRole("ADMIN");
			user1.setCustomerId("123456789");
			usersRepository.save(user1);
		};
	}

	}
