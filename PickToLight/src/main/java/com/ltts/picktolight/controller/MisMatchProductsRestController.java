package com.ltts.picktolight.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.service.MisMatchProductsService;

/**
 * @author 90001334
 *
 */

@RestController
public class MisMatchProductsRestController {

	private static final Logger logger = LoggerFactory.getLogger(MisMatchProductsRestController.class);
	@Autowired
	private MisMatchProductsService misMatchProductsService;

	/**
	 * API to get mis-match products details
	 * @return list of mis-match products details
	 *
	 */
	@RequestMapping("/getMisMatchProductsInfoToApp")
	public Map<String, List<MisMatchProducts>> getMisMatchProductsInfoToApp(){
		logger.info("getMisMatchProductsInfoToApp() method in MisMatchProductsController Class :- Start");
		Map<String, List<MisMatchProducts>> resultObject = new LinkedHashMap<String, List<MisMatchProducts>>();
		try{
			List<MisMatchProducts> misMatchProductsList = this.misMatchProductsService.getMisMatchproducts();
			if(misMatchProductsList!=null && misMatchProductsList.size()>0){
				resultObject.put("unavailableProducts",misMatchProductsList);				
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("getMisMatchProductsInfoToApp() method in MisMatchProductsController Class :- End");
		return resultObject;
	}
	
	
}
