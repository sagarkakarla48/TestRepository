/**
 * 
 */
package com.ltts.picktolight.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001334
 *
 */

@Entity
@Table(name="cartdetails")
public class ProductCartDetails {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="order_id")
	private String orderId;
	
	@Column(name="super_market_desc")
	private String superMarketDesc;
	
	@Column(name="cart_id")
	private String cartId;	

	@Column(name="datetime")
	private String datetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSuperMarketDesc() {
		return superMarketDesc;
	}

	public void setSuperMarketDesc(String supermarket) {
		this.superMarketDesc = supermarket;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
}
