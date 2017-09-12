/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.MisMatchProductsDAO;
import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */
@Service
public class MisMatchProductsServiceImpl implements MisMatchProductsService {
	
	private static final Logger logger = LoggerFactory.getLogger(MisMatchProductsServiceImpl.class);
	
	private MisMatchProductsDAO misMatchProductsDAO;
	
	public void setMisMatchProductsDAO(MisMatchProductsDAO misMatchProductsDAO) {
		this.misMatchProductsDAO = misMatchProductsDAO;
	}


	@Override
	@Transactional
	public String saveMisMatchedProducts(List<MisMatchProducts> misMatchProductsList) {
		List<MisMatchProducts> productList = getMisMatchproducts();
		if(productList!=null && productList.size()>0){
			// this is for delete existing mismatchproducts when new e-file upload
			misMatchProductsDAO.deleteExistingMismatchProducts(productList);
		}
		return misMatchProductsDAO.saveMisMatchedProducts(misMatchProductsList);
	}


	@Override
	@Transactional
	public List<MisMatchProducts> getMisMatchproducts() {
		return misMatchProductsDAO.getMisMatchproducts();
	}


	@Override
	@Transactional
	public List<MisMatchProducts> getMisMatchproductsToIncompleteOrders() {
		return misMatchProductsDAO.getMisMatchproductsToIncompleteOrders();
	}

}
