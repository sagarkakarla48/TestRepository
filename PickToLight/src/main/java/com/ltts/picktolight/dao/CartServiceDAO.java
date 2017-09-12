package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.ProductCartDetails;

public interface CartServiceDAO {

	String saveCartDetailsOfOrdersInSupermarket(ProductCartDetails cartDetails);
	
	List<ProductCartDetails> getCartDetailsOfOrdersInSupermarket();

	String updatedOrderIncompleteProducts(IncompleteOrders orderUpdate);	
}
