package com.myhome.repository;

import java.util.List;

import com.myhome.model.User;

public interface CustomizedUserRepository {
	
	List<User> findByUsernameCustom(String username);
	
	
	// jdbctemplate를 이용하여
	List<User>  findByUsernameJdbc(String username);
}
