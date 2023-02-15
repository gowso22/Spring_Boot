package com.myhome.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.myhome.model.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom{
	
	
	public List<Notice> findTop3ByOrderByIdDesc();
	
	public List<Notice> findByOrderByIdDesc();
}
