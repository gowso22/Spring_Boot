package com.myhome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	private String imgName; // 이미지 파일명
	private String oriImgName; // 원본이미지 파일명
	private String imgUrl; // 이미지 경로
	private String repimgYn; // 대표이미지 여부
	
	
	

}
