/**
 * 
 */
package com.ltts.picktolight.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 90001334
 *
 */
public class IncompleteOrders {
	private String orderId;
	private String cartId;
	private List<ProductBarcode> productDetails = new  ArrayList<ProductBarcode>();
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the cartId
	 */
	public String getCartId() {
		return cartId;
	}
	/**
	 * @param cartId the cartId to set
	 */
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	/**
	 * @return the orderDetails
	 */
	public List<ProductBarcode> getProductDetails() {
		return productDetails;
	}
	/**
	 * @param orderDetails the orderDetails to set
	 */
	public void setProductDetails(List<ProductBarcode> products) {
		this.productDetails.clear();
		this.productDetails.addAll(products);
	}	
	
	public void addProduct(ProductBarcode product) {
		this.productDetails.add(product);
	}	
	
}
