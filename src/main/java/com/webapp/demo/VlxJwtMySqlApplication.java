package com.webapp.demo;

import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class VlxJwtMySqlApplication implements CommandLineRunner {

	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(VlxJwtMySqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = userRepo.findByEmail("admin@gmail.com");
		if(user.getId() == 0){
			User user1 = new User("admin@gmail.com", "admin", "admin");
			user1.setPrimemember(true);
			User user2 = new User("buyer@gmail.com", "buyer", "buyer");
			User user3 = new User("seller@gmail.com", "seller", "seller");

			userRepo.saveAll(List.of(user1, user2, user3));
		}else{
			user.setPrimemember(true);
			userRepo.save(user);
		}
	}

}
