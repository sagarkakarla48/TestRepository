/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.MisMatchProducts;

/**
 * @author 90001332
 *
 */
public interface MisMatchProductsDAO {

	void deleteExistingMismatchProducts(List<MisMatchProducts> misMatchProductsList);
	
	String saveMisMatchedProducts(List<MisMatchProducts> misMatchProductsList);
	
	List<MisMatchProducts> getMisMatchproducts();
	
	List<MisMatchProducts> getMisMatchproductsToIncompleteOrders();
	
}
