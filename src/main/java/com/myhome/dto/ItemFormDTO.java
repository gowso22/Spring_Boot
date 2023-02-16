package com.myhome.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.myhome.constant.ItemSellStatus;
import com.myhome.model.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDTO { // 상품 데이터 정보를 전달하는 DTO 클래스

    private Long id; // 상품고유번호

    private String regDate;
    private String bookWtr; // 저자
    private String bookPsr; // 출판사
    
    @NotBlank(message = "이름을 입력해주세요")
	private String itemNm; // 상품이름
	
    @NotNull(message = "가격을 입력해주세요")
    private int price; // 상품가격

    @NotNull(message = "재고수량을 입력해주세요")
    private int stockNumber; //재고수량

    @NotBlank(message = "상세설명을 입력해주세요")
    private String itemDetail; //상품 상세 설명

    private ItemSellStatus itemSellStatus;
    
    // 상품 수정시 상품이미지 정보 저장 리스트
    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
    
//    // 상품 이미지 아이디를 저장하는 리스트
//    private List<Long> itemImgIdList = new ArrayList<>();
    
 // 상품 수정시에 이미지 id를 담아두는 용도의 리스트
    private List<Long> itemImgIds = new ArrayList<>(); 
    
    private static ModelMapper modelMapper = new ModelMapper();
    
    // 엔티티 객체와 DTO 객체의 자료형과 멤버변수의 이름이 같을 때
	// 엔티티와 DTO 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
    public Item createItem() {
    	return modelMapper.map(this, Item.class);
    }
    public static ItemFormDTO of(Item item) {
    	return modelMapper.map(item, ItemFormDTO.class);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
		
}
