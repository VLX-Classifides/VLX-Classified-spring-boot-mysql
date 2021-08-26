package com.webapp.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.demo.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
