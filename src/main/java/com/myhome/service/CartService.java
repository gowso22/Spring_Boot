package com.myhome.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myhome.dto.CartDetailDTO;
import com.myhome.dto.CartItemDTO;
import com.myhome.model.Cart;
import com.myhome.model.CartItem;
import com.myhome.model.Item;
import com.myhome.model.User;
import com.myhome.repository.CartItemRepository;
import com.myhome.repository.CartRepository;
import com.myhome.repository.ItemRepository;
import com.myhome.repository.UserRepository;

@Service
@Transactional
public class CartService {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	
	// 장바구니에 상품을 등록할 로직
	public Long addCart(CartItemDTO cartItemDTO, String username) {
		
		Item item = itemRepository.findById(cartItemDTO.getItemId()).orElseThrow(EntityNotFoundException::new); // 장바구니에 담을 상품 엔티티 조회
		User user = userRepository.findByUsername(username); // 로그인 회원 username 조회
		
		Cart cart = cartRepository.findByUserId(user.getId()); // 회원의 cart Entity 조회
		// 해당 회원이 상품을 처음 장바구니에 담을 때 회원의 장바구니 생성
		if(cart == null) {
			cart = Cart.createCart(user);
			cartRepository.save(cart);
		}
		CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
		
		
		if(savedCartItem != null) {
			// 장바구니에 이미 상품이 있다면 담을 수량만큼 더해줌
			savedCartItem.addCount(cartItemDTO.getCount());
			
			return savedCartItem.getId();
		}else {
			// 장바구니, 상품 엔티티와 장바구니에 담을 수량을 이용해 CartItem 엔티티를 생성
			CartItem cartItem =  CartItem.createCartItem(cart, item, cartItemDTO.getCount());
			cartItemRepository.save(cartItem); // 상품을 장바구니에 저장
			
			return cartItem.getId();
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<CartDetailDTO> getCartList(String username){ // 회원정보를 이용하여 장바구니에 들어있는 상품을 조회
		
		List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
		
		User user = userRepository.findByUsername(username);
		
		Cart cart = cartRepository.findByUserId(user.getId()); // 로그인한 회원의 장바구니 엔티티를 조회
		
		if(cart == null) {
			return cartDetailDTOList; // 장바구니 엔티티가 비었을 경우 새 장바구니 리스트 반환
		}
		
		
		cartDetailDTOList = cartItemRepository.findCartDetailDtoList(cart.getId());
		
		return cartDetailDTOList; // 장바구니에 담겨있는 리스트 조회
		
	}
	
//	public void deleteCartItem(Long cartItemId) {
//		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//		cartItemRepository.delete(cartItem);
//	}
	
	
	
	
}
