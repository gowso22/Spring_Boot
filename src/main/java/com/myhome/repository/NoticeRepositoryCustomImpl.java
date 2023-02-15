package com.myhome.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.myhome.model.Notice;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Notice> findByLimitJdbc() {
		
		List<Notice> notices = jdbcTemplate.query(
				"select * from notice limit 3", 
				new BeanPropertyRowMapper(Notice.class));
		
		
		return notices;
	}

}
