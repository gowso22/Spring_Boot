package com.myhome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg {

	@Id
	@Column(name = "item_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String imgName; // 이미지 파일명
	private String oriImgName; // 원본이미지 파일명
	private String imgUrl; // 이미지 경로
	private String repimgYn; // 대표이미지 여부
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	
	// 상품이미지수정위한 로직 추가
	public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
	
	
}
