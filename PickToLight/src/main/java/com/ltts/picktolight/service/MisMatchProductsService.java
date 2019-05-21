/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.List;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.MisMatchProducts;

/**
 * @author 90001332
 *
 */
public interface MisMatchProductsService {
	
	String saveMisMatchedProducts(List<MisMatchProducts> misMatchProductsList);
	
	List<MisMatchProducts> getMisMatchproducts();
	
	List<MisMatchProducts> getMisMatchproductsToIncompleteOrders();
	
}
