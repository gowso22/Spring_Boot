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
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {
	
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "cart_item_id")
	   private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="cart_id")
	    private Cart cart;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "item_id")
	    private Item item;

	    private int count;

	    //
	    public static CartItem createCartItem(Cart cart, Item item, int count) {
	        CartItem cartItem = new CartItem();
	        cartItem.setCart(cart);
	        cartItem.setItem(item);
	        cartItem.setCount(count);
	        return cartItem;
	    }
	    
	    // 장바구니에서 수량을 더해줄 때 사용
	    public void addCount(int count){ 
	        this.count += count;
	    }

}
