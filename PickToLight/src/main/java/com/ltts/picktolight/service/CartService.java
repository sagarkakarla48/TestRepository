package com.ltts.picktolight.service;

import java.util.List;
import java.util.Map;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.ProductCartDetails;
import com.ltts.picktolight.domain.UploadProducts;

public interface CartService {

	public String saveCartDetailsOfOrdersInSupermarket(ProductCartDetails cartDetails);	

	List<ProductCartDetails> getCartDetailsOfOrdersInSupermarket();
	
	String updatedOrderIncompleteProducts(IncompleteOrders orderUpdate);	

}
