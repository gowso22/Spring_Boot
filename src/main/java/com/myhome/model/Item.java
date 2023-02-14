package com.myhome.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;

import com.myhome.constant.ItemSellStatus;
import com.myhome.dto.ItemFormDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {
	
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 상품고유번호
	
	@Column(nullable = false, length = 50)
	private String itemNm; // 상품이름
	
	@Column(name="price", nullable = false)
    private int price; // 상품가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    private String regDate;
    
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;
    
    // 상품 수정을 위해 로직 추가
    public void updateItem(ItemFormDTO itemFormDTO) {
    	 this.itemNm = itemFormDTO.getItemNm();
         this.price = itemFormDTO.getPrice();
         this.stockNumber = itemFormDTO.getStockNumber();
         this.itemDetail = itemFormDTO.getItemDetail();
         this.itemSellStatus = itemFormDTO.getItemSellStatus();
	}
    
    @OneToMany(mappedBy = "item", fetch =  FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemImg> itemImgs = new ArrayList<>();
	
	
}
