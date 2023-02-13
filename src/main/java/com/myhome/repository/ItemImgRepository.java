package com.myhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhome.model.Item;
import com.myhome.model.ItemImg;

@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

	

}
