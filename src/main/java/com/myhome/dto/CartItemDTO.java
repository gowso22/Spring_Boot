package com.myhome.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDTO {

	
	
	@NotNull(message = "상품 아이디는 필수 입니다.")
	private Long itemId;
	
	@Min(value = 1, message = "최소 1개 이상 담아주시기 바랍니다.")
	private int count;
}
