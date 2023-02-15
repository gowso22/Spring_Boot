package com.myhome.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.myhome.model.Item;



// dao : 실제 db에 작업을 하기 위한 interface
// 이름 등 작성 규칙이 정해져 있다.
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>,  QuerydslPredicateExecutor<Item>, ItemRepositoryCustom  {

	

	
}