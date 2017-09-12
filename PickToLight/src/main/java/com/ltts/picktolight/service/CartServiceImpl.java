package com.ltts.picktolight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.CartServiceDAO;
import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.domain.ProductCartDetails;

public class CartServiceImpl implements CartService{

	private CartServiceDAO cartServiceDAO;
	
	public void setCartServiceDAO(CartServiceDAO cartServiceDAO) {
		this.cartServiceDAO = cartServiceDAO;
	}

	
	@Override
	@Transactional
	public String saveCartDetailsOfOrdersInSupermarket(ProductCartDetails cartDetails) {
	   
		String status=this.cartServiceDAO.saveCartDetailsOfOrdersInSupermarket(cartDetails);
		return status;
	}
	
	
	@Override
	@Transactional
	public List<ProductCartDetails> getCartDetailsOfOrdersInSupermarket() {
		return cartServiceDAO.getCartDetailsOfOrdersInSupermarket();
	}


	@Override
	@Transactional
	public String updatedOrderIncompleteProducts(IncompleteOrders orderUpdate) {
		return cartServiceDAO.updatedOrderIncompleteProducts(orderUpdate);
	}


	
}
