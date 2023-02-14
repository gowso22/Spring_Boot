package com.myhome.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDTO {

	private Long cartItemId; // 장바구니 상품 아이디
	private String itemNm; // 상품이름
	private int price; // 가격
	private int count; // 수량
	private String imgUrl; // 상품 이미지 경로
	
	//생성자 설정
	public CartDetailDTO(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
		this.cartItemId = cartItemId;
		this.itemNm = itemNm;
		this.price = price;
		this.count = count;
		this.imgUrl = imgUrl;
	}
	
	
	
}
