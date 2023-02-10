package com.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhome.model.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>{

}
