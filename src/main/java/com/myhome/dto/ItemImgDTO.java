package com.myhome.dto;

import org.modelmapper.ModelMapper;

import com.myhome.model.ItemImg;

import lombok.Getter;
import lombok.Setter;

// 상품 저장 후 상품 이미지에 대한 데이터를 전달할 DTO
@Getter
@Setter
public class ItemImgDTO {
	
	private long id; 
	private String imgName; 
	private String oriImgName; 
	private String imgUrl; 
	private String repimgYn; 
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ItemImgDTO of(ItemImg itemImg) {
		// itemimg 엔티티 객체 파라미터 받아서
		// itemimg 객체의 자료형과 멤버변수의 이름이 같을 때
		// itemImgDTO로 값을 복사해 반환
		return modelMapper.map(itemImg, ItemImgDTO.class);
	}
}
