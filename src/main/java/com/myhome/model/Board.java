package com.myhome.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


@Entity
@Data
public class Board {
	//PK 설정
	@Id
	//auto increment 선언
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(min=2, max=30, message = "제목은 2자 이상 30자 이하로 작성해주세요.")
	private String title;
	
	private String content;
}
