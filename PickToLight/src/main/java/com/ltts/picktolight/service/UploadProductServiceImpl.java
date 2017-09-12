package com.ltts.picktolight.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.UploadProductDAO;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.util.StringConstatns;
@Service
public class UploadProductServiceImpl implements UploadProductService{


	private static final Logger logger = LoggerFactory.getLogger(UploadProductServiceImpl.class);

	@Autowired
	private UploadProductDAO uploadProductDAO;


	public void setUploadProductDAO(UploadProductDAO uploadProductDAO) {
		this.uploadProductDAO = uploadProductDAO;
	}


	/**
	 * sends the product details got from excel to database
	 * @param productList list which holds product details
	 */
	@Override
	@Transactional
	public void uploadProduct(List<UploadProducts> productList) {
		logger.info("uploadProduct() method in UploadProductServiceImpl Class :- Start");

		// sending product details to DAOImplementation class
		this.uploadProductDAO.uploadProduct(productList);

		logger.info("uploadProduct() method in UploadProductServiceImpl Class :- End");
	}

	@Override
	@Transactional
	public List<ProductBarcode> getAllProductOrdersData(){
		logger.info("getAllProductOrdersData() method in UploadProductServiceImpl Class :- Start");

		// sending product details to DAOImplementation class
		List<ProductBarcode> productOrdersList = this.uploadProductDAO.getAllProductOrdersData();

		logger.info("getAllProductOrdersData() method in UploadProductServiceImpl Class :- End");
		return productOrdersList;
	}	

	/**
	 * here we are updating the datetime of product for tracking mobile device working or not
	 * @param UploadProducts contains rack id and datetime
	 * @return status success or not
	 */
	@Override
	@Transactional
	public String updateProductDateTime(UploadProducts productInfo) {

		logger.info("updateProductDateTime() method in UploadProductServiceImpl Class :- Start");

		String status = StringConstatns.failure;

		try {
			status=uploadProductDAO.updateProductDateTime(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		resultmap.put("responseCode", status);

		logger.info("updateProductDateTime() method in UploadProductServiceImpl Class :- End");
		return status;
	}

	/**
	 * here we are getting the product info for tracking mobile device working or not
	 * @param UploadProducts
	 * @return products which devices are not working
	 */
	@Override
	@Transactional
	public List<UploadProducts> getProductInfoForDeviceTracking(UploadProducts productDetails) {
		logger.info("getProductInfoForDeviceTracking() method in UploadProductServiceImpl Class :- Start");
		List<UploadProducts> productlist=new ArrayList<UploadProducts>();
		try {
			productlist=uploadProductDAO.getProductInfoForDeviceTracking(productDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}
		logger.info("getProductInfoForDeviceTracking() method in UploadProductServiceImpl Class :- End");
		return productlist;
	}


	@Override
	@Transactional
	public List<UploadProducts> getSupermarketsNotHavingProducts(UploadProducts productInfo) {
		logger.info("getSupermarketsNotHavingProducts() method in UploadProductServiceImpl Class :- Start");
		List<UploadProducts> productlist=new ArrayList<UploadProducts>();
		try {
			productlist=uploadProductDAO.getSupermarketsNotHavingProducts(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}
		logger.info("getSupermarketsNotHavingProducts() method in UploadProductServiceImpl Class :- End");
		return productlist;
	}


	@Override
	@Transactional
	public List<UploadProducts> getSuperMarketsNotHavingAnyProducts(List<ProductBarcode> productOrdersList) {
		logger.info("getSuperMarketsNotHavingAnyProducts() method in UploadProductServiceImpl Class :- Start");
		List<UploadProducts> supermarketList=new ArrayList<UploadProducts>();
		UploadProducts productInfo = new UploadProducts();
		try {
			for(ProductBarcode productDetails:productOrdersList){
				UploadProducts productOrder = null;
				productInfo.setProductId(productDetails.getUploadProducts().getProductId());
				productInfo.setSuperDescription(productDetails.getSuperMarketDesc());
				productOrder = uploadProductDAO.getSuperMarketsNotHavingAnyProducts(productInfo);
				if(productOrder!=null){
					supermarketList.add(productOrder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}
		logger.info("getSuperMarketsNotHavingAnyProducts() method in UploadProductServiceImpl Class :- End");
		return supermarketList;
	}


	@Override
	@Transactional
	public List<UploadProducts> getProductInfo(UploadProducts productDetails) {
		logger.info("getProductInfo() method in UploadProductServiceImpl Class :- Start");

		List<UploadProducts> productOrdersList = this.uploadProductDAO.getProductInfo(productDetails);

		logger.info("getProductInfo() method in UploadProductServiceImpl Class :- End");
		return productOrdersList;
	}




}
