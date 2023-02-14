package com.myhome.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.myhome.constant.ItemSellStatus;
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
	    
	    @Enumerated(EnumType.STRING)
	    private ItemSellStatus itemSellStatus;

	    @QueryProjection // Querydsl로 결과 조회 시 MainItemDTO 객체로 바로 받아오도록 활용
	    public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl,Integer price, ItemSellStatus itemSellStatus){
	        this.id = id;
	        this.itemNm = itemNm;
	        this.itemDetail = itemDetail;
	        this.imgUrl = imgUrl;
	        this.price = price;
	        this.itemSellStatus = itemSellStatus;
	    }
	
}
