package com.myhome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myhome.dto.CartDetailDTO;
import com.myhome.model.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByCartIdAndItemId(Long cartId, Long itemId);
	
	
	
	
	
	 @Query("select new com.myhome.dto.CartDetailDTO(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
	            "from CartItem ci, ItemImg im " +
	            "join ci.item i " +
	            "where ci.cart.id = :cartId " +
	            "and im.item.id = ci.item.id " +
	            "and im.repimgYn = 'Y' " +
	            "order by ci.id desc"
	            )
	    List<CartDetailDTO> findCartDetailDtoList(Long cartId);
	
}
