package com.myhome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.myhome.model.User;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>,  CustomizedUserRepository  {

	User findByUsername(String username);
	
	 @Query("select u from User u where u.username like %?1%") // ?1 >>1번째 파라미터
	  List<User> findByUsernameQuery(String username);
	 
	// nativeQuery = true >> 보통 아는 형식의 sql문을 작성할 수 있게 함
	 @Query(value =  "select * from User u where u.username like %?1%", nativeQuery = true) 
	  List<User> findByUsernameNativeQuery(String username);
	 
}
