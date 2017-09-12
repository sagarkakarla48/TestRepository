package com.ltts.picktolight.service;

import java.util.List;

import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;

public interface UploadProductService {
	
	public void uploadProduct(List<UploadProducts> productList);

	public List<ProductBarcode> getAllProductOrdersData();

	public String updateProductDateTime(UploadProducts prodbean);
	
	public List<UploadProducts> getProductInfoForDeviceTracking(UploadProducts productDetails);
	
	public List<UploadProducts> getProductInfo(UploadProducts productDetails);
	
	public List<UploadProducts> getSupermarketsNotHavingProducts(UploadProducts productInfo);
	
	public List<UploadProducts> getSuperMarketsNotHavingAnyProducts(List<ProductBarcode> productOrdersList);
}
