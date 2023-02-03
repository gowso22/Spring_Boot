package com.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myhome.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> { // JpaRepository<테이블명, 기본키데이터타입>

}
