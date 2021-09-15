package com.webapp.demo;

import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VlxJwtMySqlApplication implements CommandLineRunner {

	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(VlxJwtMySqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		User user = userRepo.findByEmail("admin@gmail.com");
		if(user.getId() == 0){
			User user1 = new User("admin@gmail.com", "admin", "admin");
			User user2 = new User("buyer@gmail.com", "buyer", "buyer");
			User user3 = new User("seller@gmail.com", "seller", "seller");
			userRepo.save(user1);
			userRepo.save(user2);
			userRepo.save(user3);
		}
	}

}
