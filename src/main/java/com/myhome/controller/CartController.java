package com.myhome.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myhome.dto.CartDetailDTO;
import com.myhome.dto.CartItemDTO;
import com.myhome.model.CartItem;
import com.myhome.repository.CartItemRepository;
import com.myhome.repository.CartRepository;
import com.myhome.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@PostMapping("/cart")
	public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDTO cartItemDTO, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){ // CartItemDTO에 설정해놓은 유효성 검사 실행
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String username = principal.getName();
        Long cartItemId;

        try {
        	// controller >> service
        	// 화면으로 부터 넘어온 장바구니에 담을 상품 정보와 현재 로그인 회원의 username을 이용하여 장바구니에 담는 로직호출
            cartItemId = cartService.addCart(cartItemDTO, username); 
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK); // 결과값으로 생성된 장바구니 상품 아이디와 요청이 성공하였다는 HTTP 응답코드로 반환
    }
	
	@GetMapping("/cart")
	public String cartList(Principal principal, Model model) {
		List<CartDetailDTO> cartDetailList = cartService.getCartList(principal.getName()); // 로그인한 username 이용하여 장바구니 상품정보를 조회
		 model.addAttribute("cartItems", cartDetailList);
		 
		 return "cart/cartList";
		
	}
	
	@GetMapping("/deleteCartItem")
	public String deleteCartItem(@RequestParam(required = false) Long cartItemId){
		
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
		
        cartItemRepository.delete(cartItem);
        

        return "redirect:/cart";
    }
	
}
