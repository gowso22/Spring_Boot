package com.myhome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {
	
		@Id // PK 기본키
	    @Column(name = "cart_id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    // 방향성 카트입장에서만 설정을 함, 이부분을 방향성으로 표현 : 단방향
	    // cart와 member 연관관계 매핑, 일대일 관계
	    // EAGER : 즉시로딩, LAZY : 지연로딩
	    @OneToOne(fetch = FetchType.LAZY) 
	    // member에 PK 역할을 하는 멤버를 지정했음.
	    @JoinColumn(name = "user_id", referencedColumnName = "id")
	    private User user;

	    // user 엔티티를 파라미터로 받아서 cart 엔티티를 생성로직
	    public static Cart createCart(User user){
	        Cart cart = new Cart();
	        cart.setUser(user);
	        return cart;
	    }
}
