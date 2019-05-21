/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.List;
import java.util.Map;

import com.ltts.picktolight.domain.ColorPicker;
import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;

/**
 * @author 90001332
 *
 */
public interface ProductService {

	Map<String,List<ProductBarcode>> addProduct(List<ProductBarcode> prodBarcodeList);
	
	Map<String, String> updateProduct(ProductBarcode p);
	
	String makeOrderedProductActive(ProductBarcode prodBarcode);
	
	List<ProductBarcode> checkOrderStaus(ProductBarcode orderid);
	
	String getColorCode();
	
	String updateColorStatus(ColorPicker cpobj);
	
	Map<String, List<Map<String, String>>> getProductDetailsByRackId(UploadProducts products);
	
	List<ProductBarcode> getOrderStatus();
	
	long getOrderCompletionStatus(ProductBarcode prodBarcode);
	
	List<Map<String, String>> getNotPickedProductDetails(ProductBarcode prodBarcode);
	
	String checkForCompletedOrders();

	List<ProductBarcode> getProductOrdersWithActive(ProductBarcode prodBarcode);

	List<ProductBarcode> countTimePeriodForOrder(ProductBarcode productBarcode);
	
	List<ProductBarcode> getOrdersToIncompleteOrders();
	
	List<ProductBarcode> getIncompleteOrderProducts(ProductBarcode products);
	
    String updateForceCompleteStatus(ProductBarcode prodBarcode);
	
}
