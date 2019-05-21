/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;

/**
 * @author 90001332
 *
 */
public interface ProductDAO {
	
	public void addProduct(List<ProductBarcode> orderProdList);
	
	public int updateProduct(ProductBarcode p);
	
	public String makeOrderedProductActive(ProductBarcode prodBarcodeList);
	
	public List<ProductBarcode> checkOrderStaus(ProductBarcode orderid);
	
	public UploadProducts getProductDescriptionDetails(ProductBarcode productDetails,String checkFor);
	
	public UploadProducts getProductDetailsByRackId(UploadProducts products);
	
	public List<Object[]> getOrderStatus();
	
	public long getOrderCompletionStatus(ProductBarcode productDetails);
	
	List<ProductBarcode> getNotPickedProductDetails(ProductBarcode prodBarcode);
	
	public String deletePickedOrders(List<ProductBarcode> orderProdList);
	
	public List<ProductBarcode> getProductOrdersWithActive(ProductBarcode prodBarcode);
	
	public List<ProductBarcode> countTimePeriodForOrder(ProductBarcode productBarcode);		
	
	public List<ProductBarcode> getOrdersToIncompleteOrders();
	
	public List<ProductBarcode> getIncompleteOrderProducts(ProductBarcode productBarcode);
	
    public String updateForceCompleteStatus(ProductBarcode prodBarcode);

}
