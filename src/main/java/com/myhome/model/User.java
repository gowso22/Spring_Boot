package com.myhome.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myhome.dto.UserDTO;

import lombok.Data;

@Entity
@Data
public class User {
	// PK 설정
	@Id
	// auto increment 선언
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String username;
	
	private String password;
	private boolean enabled;
	private String addr1;
	private String addr2;
	private String addr3;
	
	public static User toUpdateUser(UserDTO userDTO) {
		User user = new User();
		
		user.setId(userDTO.getId());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEnabled(true);
		
		return user;
	}
	
	// join을 위해 연관관계 매핑
	@ManyToMany
	@JoinTable(
			name = "user_role", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();
	
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // mappedBy >> 양방향 매핑 >> 단방향 매핑(joinTable) 시 Many 쪽에서 설정
	// FetchType.EAGER >> USER 정보 조회시 BOARD가 같이 조회
	// FetchType.LAZY >>  BOARD 사용시에만 BOARD 조회
	// default >> FetchType.EAGER >> @OneToOne, @ManyToOne
	// default >> FetchType.LAZY >>@OneToMany, @ManyToMany
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 상품리뷰
	private List<Board> boards = new ArrayList<>();
	@OneToMany(mappedBy = "user", fetch =  FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 공지사항
	private List<Notice> notices = new ArrayList<>();
	@OneToMany(mappedBy = "user", fetch =  FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 공지사항
	private List<Cart> carts = new ArrayList<>();
	
	
	
	
	
	
	
	
	
}
