/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;

/**
 * @author 90001334
 *
 */
public interface UploadProductDAO {

	public void uploadProduct(List<UploadProducts> productList);

	public List<ProductBarcode> getAllProductOrdersData();	
	
	public String updateProductDateTime(UploadProducts productDetails);
	
	public List<UploadProducts> getProductInfoForDeviceTracking(UploadProducts productDetails);
	
	public List<UploadProducts> getProductInfo(UploadProducts productDetails);
	
	public List<UploadProducts> getSupermarketsNotHavingProducts(UploadProducts productInfo);
	
	public UploadProducts getSuperMarketsNotHavingAnyProducts(UploadProducts productInfo);
	
}


