package com.myhome.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myhome.dto.ItemSearchDTO;
import com.myhome.dto.MainItemDTO;

public interface ItemRepositoryCustom {

	
	Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}
