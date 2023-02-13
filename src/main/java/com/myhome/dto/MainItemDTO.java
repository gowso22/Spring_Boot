package com.myhome.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDTO { // 메인페이지에 상품 보여줄 때 사용
	
	 private Long id;

	    private String itemNm;

	    private String itemDetail;

	    private String imgUrl;

	    private Integer price;

	    @QueryProjection // Querydsl로 결과 조회 시 MainItemDTO 객체로 바로 받아오도록 활용
	    public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl,Integer price){
	        this.id = id;
	        this.itemNm = itemNm;
	        this.itemDetail = itemDetail;
	        this.imgUrl = imgUrl;
	        this.price = price;
	    }
	
}
