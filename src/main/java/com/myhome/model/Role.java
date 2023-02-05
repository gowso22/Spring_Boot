package com.myhome.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Data
public class Role {
	//PK 설정 
	@Id
	//auto increment 선언
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@ManyToMany(mappedBy = "roles") 
	// @JsonIgnore >> api 테스트 시 user와 role 서로가 서로를 참조하므로 
	// user안에 role이있고  role안에 user가 있게 되어서 계속해서 참조 되는 것을 방지하기 위함 

	
	private List<User> users;
	
}
