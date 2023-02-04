package com.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myhome.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {

	User findByUsername(String username);
	
	
}