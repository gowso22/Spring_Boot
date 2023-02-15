package com.myhome.repository;

import java.util.List;

import com.myhome.model.Notice;


public interface NoticeRepositoryCustom {

	List<Notice>  findByLimitJdbc();
	
	
}
