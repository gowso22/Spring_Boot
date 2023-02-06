package com.myhome.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.myhome.model.QUser;
import com.myhome.model.User;
import com.querydsl.jpa.impl.JPAQuery;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository{

	@PersistenceContext
	private EntityManager em;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<User> findByUsernameCustom(String username) {
		QUser qUser = QUser.user;
		
		JPAQuery<?> query = new JPAQuery<Void>(em);
		
		// fetch >> list type
		List<User> users = query.select(qUser).from(qUser).where(qUser.username.contains(username)).fetch();
		
		return users;
		
		
	}

	// jdbctemplate 사용
	@Override
	public List<User> findByUsernameJdbc(String username) {
		
		//queryforobject >> 단일 데이터 조회, query
		// sql문장에 따로 String 타입 변수로 지정하여 넣어도 됨
		List<User> users = jdbcTemplate.query(
				"select * from user where username like ?",
				new Object[]{"%" + username + "%"}, // ? 파라미터 값 설정
				new BeanPropertyRowMapper(User.class));
		return users;
	}

}
