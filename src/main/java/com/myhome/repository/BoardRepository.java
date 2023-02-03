package com.myhome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myhome.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> { // JpaRepository<테이블명, 기본키데이터타입>
	
	
	List<Board> findByTitle(String title); // title 기준으로 Board 테이블 조회
	List<Board> findByTitleOrContent(String title, String content); // title OR content로 Board 테이블 조회 
}
