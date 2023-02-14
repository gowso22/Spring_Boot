package com.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myhome.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUserId(Long userId);
	
}
