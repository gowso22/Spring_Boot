package com.myhome.dto;

import com.myhome.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDTO {

    private String searchDateType; // 상품 등록일 기준

    private ItemSellStatus searchSellStatus; // 상품 판매상태 기준

    private String searchBy; // 상품 조회 유형

    private String searchQuery = ""; // 검색어 저장 변수

}